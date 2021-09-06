# ble_soup
#Installation

Step 1. Add it in your root build.gradle at the end of repositories:

<code>
   { 
      repositories {
        maven { url 'https://jitpack.io' }
     }
   }
 </code>
  
Step 2. Add the dependency

    dependencies {
	        implementation 'com.github.droideveloper1996:ble_soup:Tag'
	  }
    
 
  #Scanning for devices
      BleScanner scanner;

      BleScanner scanner;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "Hello from ble soup", Toast.LENGTH_SHORT).show();
        handler = new Handler(Looper.getMainLooper());
        scanner = new BleScanner(this, handler, callback);
        try {
            ArrayList<String> deviceMac = new ArrayList<>();
            deviceMac.addAll(Arrays.asList(mac));
            scanner.startScan();
        } catch (BleException bleException) {
            bleException.printStackTrace();
        }
    }
#You need to pass scan callback to the scanner object
@BleScanCallback is the abstract class that is used as callback

      BleScanCallback callback = new BleScanCallback() {
        @Override
        public void onDeviceDiscovered(BleDevice bleDevice) {
            super.onDeviceDiscovered(bleDevice);
        }

        @Override
        public void onScanFinished() {
            super.onScanFinished();
        }

        @Override
        public void onScanFailed() {
            super.onScanFailed();
        }

        @Override
        public void onDeviceConnected() {
            super.onDeviceConnected();
        }

        @Override
        public void onBondLost() {
            super.onBondLost();
        }

        @Override
        public void onBondingFailed() {
            super.onBondingFailed();
        }

        @Override
        public void onBondSucceed() {
            super.onBondSucceed();
        }

        @Override
        public void onAdapterStateChange() {
            super.onAdapterStateChange();
        }
    };

#BleDeviceCallback
  This callback reports you back from the result of asyncronous operation like read,write,notifiy,indicate.
  Callback are executed when corresponding operations are performed

    private BleDeviceCallback bleDeviceCallback = new BleDeviceCallback() {
        @Override
        public void onCharacteristicWrite() {
            super.onCharacteristicWrite();
        }

        @Override
        public void onCharacteristicRead() {
            super.onCharacteristicRead();
        }

        @Override
        public void onCharacteristicsChanged() {
            super.onCharacteristicsChanged();
        }

        @Override
        public void onNotificationStateChange() {
            super.onNotificationStateChange();
        }

        @Override
        public void onFailedServiceDiscovery() {
            super.onFailedServiceDiscovery();
        }

        @Override
        public void onServicesDiscovered() {
            super.onServicesDiscovered();
        }

        @Override
        public void onMTUChanged() {
            super.onMTUChanged();
        }

        @Override
        public void onWriteFailed() {
            super.onWriteFailed();
        }

        @Override
        public void onDescriptorRead() {
            super.onDescriptorRead();
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onDeviceDisconnected() {
            super.onDeviceDisconnected();
           
        }
    };
    
    #Operation Queue
    
    boolean flag which enables or disabled the read write operations in the queue.
    flag=true makes the operation in the queue and then it is executed accordingly
    
    #Long Read/Write
    
    Any data size that is greater than the MTU size is automatically handled and the data is sent to the device as chunks
    in case of the failed read/write operation library attemts to read/write the characteristic after 5 sec, this behaviour
    can be stopped by passing flag retyCount=0,delay=0;
    
    For notification data greater than the defined size[200 byte] is clubbed together and then returned as the string;
    


