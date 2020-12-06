function VerihubsCordova() { }

VerihubsCordova.prototype.initClass = function (
  successCallback,
  errorCallback
) {
  cordova.exec(successCallback, errorCallback, "VerihubsWrapper", "initClass", null);
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
    "VerihubsWrapper",
    "verifyLiveness",
    [instructions_count, timeout]
  );
};

VerihubsCordova.prototype.getVersion = function (
  successCallback,
  errorCallback
) {
  cordova.exec(successCallback, errorCallback, "VerihubsWrapper", "getVersion", null);
};

VerihubsCordova.install = function () {
  if (!window.plugins) {
    window.plugins = {};
  }
  window.plugins.verihubssdk = new VerihubsCordova();
  return window.plugins.verihubssdk;
};
cordova.addConstructor(VerihubsCordova.install);
