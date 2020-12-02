# Verihubs Cordova Plugin

Verihubs Cordova Plugin is our SDK that has been ported to Cordova.

## Installation

Point your plugin url settings to this [repo](https://gitlab.com/verihubspublic/cordova-plugin.git)

## Functions

There are currently 3 methods in our plugin.

### Initializing Class

To use our plugin, we must first initialize the plugin. If successful it will return a success callback.

### Verify Liveness

You can use our liveness detection method by supplying it a count of how many instructions will be provided and a time limit for finishing the active part of our liveness detection process. This function will return a status code, number of instructions given, the instructions given and images taken during the detection process in Base64 form. The number of instructions and images returned correlates to the number of instruction given and you can access it by appending a number (starting from 1) as shown in our example below.

<aside class="notice">
Status code 200 indicates that the process has run successfully, status code 401 indicates that the active part has not run successfully, status code 402 indicates that the passive part has not run successfully and status code 403 indicates the process has been cancelled.
</aside>

### Get Version

You can check our SDK version by calling this method.

## Usage

```js
window.plugins.verihubs.initClass(initSuccess, initError);

function initSuccess() {
    alert("SDK Initialization success");
}

function initError(error) {
    alert(error);
}

window.plugins.verihubs.verifyLiveness(
      instruction_count,
      timeout,
      livedetSuccess,
      livedetError
    );

function livedetSuccess(result) {
    alert(result.status);
    alert(result.total_instruction);
    alert(result.instruction1);
    alert(result.base64String_1);
}

function livedetError(error) {
    alert(error);
}

window.plugins.verihubs.getVersion(getVersionSuccess, getVersionError);

function getVersionSuccess(result) {
    alert(result.version);
}
function getVersionError(error) {
    alert(error);
}
```


## License
JRL
