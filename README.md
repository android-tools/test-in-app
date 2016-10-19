# test-in-app
Tools to run your tests inside the app

# Steps to run test (beta)
1. assemple test apk file
2. push test apk file using adb to emulator or device (below 6) (push apk file strictly to path=/sdcard/test.apk)
3. install work app on device and click on `Try start test inside!`

You should see
1. app will be terminated
2. after few seconds ap will be restarted
3. 2 test will be run
4. after few seconds app will be reopened on test result page
