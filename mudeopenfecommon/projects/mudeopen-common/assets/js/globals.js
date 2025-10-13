var $$ = {
	version: '1.70.01',
	isLocal: 0, 
	isEnvDev: 0, 
	isEnvTest: 0, 
	isBackoffice: 0, 
	isBOUser: false,
	debug: 0, 
	logLevel: 0,
	step: 0,
	spinArray: [],

	//private
	lastLoggedString: null,
	lastjson: null,

	setDebugEnv: function () {
		let href = location.href.split('?');

		if(!(this.logLevel = ((this.isLocal = location.href.startsWith('http://localhost') 
								|| location.href.startsWith('http://requestly:')) 
								|| ((this.isEnvDev = location.href.indexOf('dev-fo-mudeopen.nivolapiemonte.it/')>-1))?1:0)
								|| ((this.isEnvTest = location.href.indexOf('tst-mudeopen-fo-rp.nivolapiemonte.it/')>-1)?1:0))
					|| !(href.length == 2 && (href = href[1]))) {
			return;
		}

		href.split('&').forEach(token => {
			var par = token.split('=');

			if(par[0] == 'debug') {
				this.debug = par[1];
			}
			if(par[0] == 'log') {
				this.logLevel = par[1];
			}
			if(par[0] == 'step') {
				this.step = par[1];
			}
		});
	},
	verbose: function (str, json = null) { console.debug('-verbose-------' + str + ' ' + (!json?'':JSON.stringify(json))) },
	warn: function (str, json = null) { console.warn('-warn-------' + str + ' ' + (!json?'':JSON.stringify(json))) },
	error: function (str, json = null) { console.error('-error-------' + str + ' ' + (!json?'':JSON.stringify(json))) },
	log: function (str, json = null, loglev = 2, format = "font-size: 10px;color: grey;", formatLabel = "color: #d7c700;", jindent = null) {
		if(this.lastLoggedString == str && this.lastjson == json) { return; }
		this.lastLoggedString = str; this.lastjson = json;

		if(loglev >= this.logLevel) {
			console.log('-'+loglev+'-------%c' + str + ' ' + (!json?'':'%c'+JSON.stringify(json, null, jindent || (json && JSON.stringify(json).length<1000 && '\t'))), formatLabel, json?format:"")
			if(json) {console.log(json)}
		}
	},
	log2: function (str, json = null) { this.log(str, json, 2); },
	log3: function (str, json = null) { this.log(str, json, 3, "font-size: 10px;color: blue;", "color: #d7c700;", "\t"); },
	log4: function (str, json = null) { this.log(str, json, 4); },
	log5: function (str, json = null) { this.log(str, json, 5); },
	logApi: function (apiname, params, format=null) { this.log('☑️APIcall: ' + apiname, params, 1, "", format?format:"font-size: 12px;color: blue;"); },
	logApiResult: function (apiname, params, format=null) { this.log('✅APIres: ' + apiname, params, 1, "", format?format:"font-size: 12px;color: green;"); },

	spinnerPush: function() { this.spinArray.push('+'); },
	spinnerPop: function() { this.spinArray.pop();return !this.spinArray.length; },
	getUrlParamValue(name) {
		var res=null;
		(location.href.split('?')[1] || '').split('&').forEach(token => {
			var par = token.split('=');
			if(par[0] == name) {
				res = par[1] || null;
			}
		});
		return res;
	},
	toolboxWindowValues: {},
	toolboxWindowParams: {},
	toolboxWindowPopup: null,
	openToolboxWindow: function(content) {
		if(!this.isLocal && !this.isEnvDev && !this.isBOUser) {return;}

		this.toolboxWindowPopup = window.open('','toolboxWindow','left='+(screen.availWidth-600)+',width=600,height='+screen.availHeight);
		if(!this.toolboxWindowPopup.setTabContents) {
			this.toolboxWindowPopup.document.write(content); }

		setTimeout(x => {
			this.toolboxWindowPopup.document.title = 'MUDE Toolbox Window';
			this.toolboxWindowPopup.focus();
			this.toolboxWindowPopup.setTabContents(this.toolboxWindowValues, this.toolboxWindowParams);
		 }, 100);
	},
	setToolboxWindowPopupValue(tab, value, params = null) {
		try {
			this.toolboxWindowValues[tab] = value;
			this.toolboxWindowParams[tab] = params;
			 
			if(this.toolboxWindowPopup) {
				this.toolboxWindowPopup.setTabContent(tab, value, params); }
		} catch(ignore) {}
	},
	setFocusOnNextButton() {
		var nxtButton = document.getElementById('nextStepperButton');
		if(nxtButton) {
			nxtButton.focus(); }
	},
	setFocusOnCancelButton() {
		var nxtButton = document.getElementById('cancelStepperButton');
		if(nxtButton) {
			nxtButton.focus(); }
	}

}
