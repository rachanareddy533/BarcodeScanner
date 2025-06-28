# BarcodeScanner
Android application that enables scanning of QR codes and various barcode formats using the device camera.   Allows switching between front and back cameras, and toggling the flashlight for scanning in low-light conditions.
## Features:
  - Scans both QR codes and multiple barcode formats using the camera.
  - Displays the scanned code result in a TextView on the screen.
  - Allows switching between front and back cameras via the menu.
  - Enables or disables the flashlight while scanning (torch mode).
  - Automatically checks and requests camera permission at runtime.
  - Pauses the scanner when the app is not in focus and resumes it when active.

## TechnologiesUsed:
  - java
  - androidSdk
  - cameraApi
  - zxingEmbeddedLibrary
  - androidPermissions
  - androidToolbarMenu

## Libraries:
  - com.google.zxing
  - com.journeyapps.barcodescanner

## BarcodeFormatsSupported:
  - qrCode
  - code128
  - code39
  - code93
  - pdf417
  - dataMatrix

usageSteps:
  - launchApplication
  - grantCameraPermission
  - pointCameraAtCode
  - scannedTextAppearsInTextView
  - useMenuToToggleFlashOrSwitchCamera

uiElements:
  - decoratedBarcodeView
  - textViewForScannedData
  - optionsMenuWithCameraAndFlashToggle
