/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;

export function checkBoolJsSafeExpression(expression: string, jsondata: any, quadroData: any, scoped_data: any = null, scopename = ''): boolean {
  if(!expression) { return true; }

  const result: any = checkJsSafeExpression(expression, jsondata, quadroData, scoped_data);
  $$.setToolboxWindowPopupValue('JEspr', null, expression);

  if(result === undefined) {
    return false; } // default in case of exception
  
  return !!result;
}

/* 
* nome metodo "evalExpression"; method description: 
* @param ()
* @returns true/false
*/ 

export function checkJsSafeExpression(expression: string, jsondata: any, quadroData: any, scoped_data: any = null, wapi: any = {}): any {
  // preserves string
	let _expr = expression;
	const stringsArr = [];
  if(_expr) {
    for(var i=0; i<expression.length; i++) {
      if(expression.charAt(i) == "'" || expression.charAt(i) == '"') {
        var stopIndex = expression.indexOf(expression.charAt(i), i+1);
        if(stopIndex == -1) break; // no end quote found, the quit!
  
        stringsArr.push(expression.substring(i, stopIndex+1));
        expression = expression.substring(0, i)+'§'+(stringsArr.length-1)+'§'+expression.substring(stopIndex+1);
      }
    }
  }

  // removes from 'expression' all the dangerous stuff
  let expr = _replaceAll(expression);

  // restores strings
  if(_expr) {
    stringsArr.forEach((x, index) => {
      expr = expr.replace('§'+index+'§', x);
    });
  }

  return _getExpressionResult(expr, scoped_data, jsondata, quadroData, wapi);
}


function _getExpressionResult(expr, scoped_data, jsondata, quadroData, wapi): any {
  let result: any = undefined;
  try {
    result = (new Function('$', '_', '$$', 'try { return ('+ expr + ') } catch(ex) { return "exprException: " + ex; }'))
          .bind(scoped_data?scoped_data:jsondata)(quadroData, jsondata, wapi);
  }catch(ex) { }

  if(result && (''+result).startsWith('exprException:')) {
    $$.log2('JDATAcheck exception: "' + expr + '" raised ' + result + '. Using data: ', quadroData);
    return undefined;
  }

  return result;
}


function _replaceAll(expression) {
  return (' '+expression+' ').replace(/ new /g, ' { OPERATOR "new" NOT ALLOWED! } ')
            .replace(/ delete /g, ' { OPERATOR "delete" NOT ALLOWED! } ')
            .replace(/[^a-zA-Z0-9.]window[.]/g, ' { object "{ OPERATOR "window" NOT ALLOWED! }" NOT ALLOWED! } ')
            .replace(/[^a-zA-Z0-9.]document[.]/g, ' { object "document" NOT ALLOWED! } ')
            .replace(/[^=!]=[^=>]/g, ' { OPERATOR "=" NOT ALLOWED! } ')
            .replace(/[ ]/g, '  ')
            // pre-handle functions
            .replace(/([^a-zA-Z._])([a-zA-Z][a-zA-Z.]*\()/g, '$1_$2')
            // pre-handle 'true'
            .replace(/([^a-zA-Z0-9._])(true|false|return)([^a-zA-Z0-9._])/g, '$1_$2_$3')
            // binds "JSON." to current tab key
            .replace(/([^a-zA-Z0-9._])JSONDATA[.]/g, '$1\_.')
            // binds "JSON_QUADRO." to current tab key
            .replace(/([^a-zA-Z0-9._])JSONQUADRO[.]/g, '$1\$.')
            // binds "this." to map keys
            .replace(/this[.]/g, '')
            .replace(/\b(this)\b/g, '£')
            .replace(/([^a-zA-Z0-9._'"])([a-zA-Z])/g, '$1this.$2')
            // post-handle 'true/false'
            .replace(/([^a-zA-Z0-9._])_(true|false|return)_([^a-zA-Z0-9._])/g, '$1$2$3')
            // post-handle functions
            .replace(/_([a-zA-Z][a-zA-Z.]*\()/g, '$1')
            //.replace(/([^a-zA-Z0-9._'"])@([a-zA-Z])/g, '$1this.WAPI$2')
            .replace(/£/g, 'this');
}