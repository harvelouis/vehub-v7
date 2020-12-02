function VerihubsCordova() { }

VerihubsCordova.prototype.initClass = function (
  successCallback,
  errorCallback
) {
  cordova.exec(successCallback, errorCallback, "VerihubsPlugin", "initClass", null);
};

VerihubsCordova.prototype.verifyLiveness = function (
  instructions_count,
  timeout,
  successCallback,
  errorCallback
) {
  cordova.exec(
    successCallback,
    errorCallback,
    "VerihubsPlugin",
    "verifyLiveness",
    [instructions_count, timeout]
  );
};

VerihubsCordova.prototype.getVersion = function (
  successCallback,
  errorCallback
) {
  cordova.exec(successCallback, errorCallback, "VerihubsPlugin", "getVersion", null);
};

VerihubsCordova.install = function () {
  if (!window.plugins) {
    window.plugins = {};
  }
  window.plugins.verihubs = new VerihubsCordova();
  return window.plugins.verihubs;
};
cordova.addConstructor(VerihubsCordova.install);
