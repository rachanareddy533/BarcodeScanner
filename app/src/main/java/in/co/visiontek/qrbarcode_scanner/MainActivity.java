package in.co.visiontek.qrbarcode_scanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;
import com.journeyapps.barcodescanner.camera.CameraManager;
import com.journeyapps.barcodescanner.camera.CameraSettings;

import java.util.Arrays;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {
    private static final int CAMERA_PERMISSION_REQUEST =11 ;
    DecoratedBarcodeView decoratedBarcodeView;
    TextView text;
    CameraSettings cameraSettings;
    CameraManager cameraManager;
    boolean isCameraFrontFacing = false;
    boolean isFlashOn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        decoratedBarcodeView = findViewById(R.id.scanner);
        text = findViewById(R.id.txtView);
        cameraSettings = decoratedBarcodeView.getBarcodeView().getCameraSettings();
        cameraSettings.setAutoTorchEnabled(true);

        decoratedBarcodeView = initializeBarcodeView();
      /*  cameraManager = new CameraManager(getApplicationContext());
        cameraManager.setTorch(true);*/

    }

    private DecoratedBarcodeView initializeBarcodeView() {
        DecoratedBarcodeView decoratedBarcodeView = findViewById(R.id.scanner);
        Collection<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_128, BarcodeFormat.CODE_39, BarcodeFormat.CODE_93, BarcodeFormat.PDF_417, BarcodeFormat.DATA_MATRIX);
        decoratedBarcodeView.setDecoderFactory(new DefaultDecoderFactory(formats));
        decoratedBarcodeView.initializeFromIntent(getIntent());
        decoratedBarcodeView.decodeContinuous(callback);
        decoratedBarcodeView.setTorchOff();
        return decoratedBarcodeView;
    }

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result.getText() != null) {
                text.setText(result.getText());
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionsmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.camera_menu:
                switchCamera();

                break;
            case R.id.flash_menu:
                if (isFlashOn) {
                    decoratedBarcodeView.setTorchOff();
                    isFlashOn = false;
                    item.setIcon(R.drawable.flash_off);
                } else {
                    decoratedBarcodeView.setTorchOn();
                    isFlashOn = true;
                    item.setIcon(R.drawable.flash_on);
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void switchCamera() {
        decoratedBarcodeView.pause();
        if (isCameraFrontFacing) {
            cameraSettings.setRequestedCameraId(Camera.CameraInfo.CAMERA_FACING_BACK);
            cameraSettings.setAutoTorchEnabled(true);
            isCameraFrontFacing = false;
        } else {
            cameraSettings.setRequestedCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT);
            cameraSettings.setAutoTorchEnabled(true);
            isCameraFrontFacing = true;
        }

        decoratedBarcodeView.getBarcodeView().setCameraSettings(cameraSettings);
        decoratedBarcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        decoratedBarcodeView.pause();
        text.setText("");
    }

    @Override
    protected void onStop() {
        super.onStop();
         text.setText("");
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST);
        } else {
            decoratedBarcodeView.resume();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                decoratedBarcodeView.resume();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}