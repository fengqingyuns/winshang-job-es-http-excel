    function Encrypt(word,sKey){  
       var key = CryptoJS.enc.Utf8.parse(sKey);   //加密密钥
       var srcs = CryptoJS.enc.Utf8.parse(word);  
       var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode:CryptoJS.mode.ECB,pad:CryptoJS.NoPadding});  
       return encrypted.toString();  
     };

     // 日期格式
     function FormatDate(strTime,formtData) {
         return new Date(strTime).Format(formtData);
      }
      /**
       * 注入日期格式
       * @param fmt
       * @returns {*}
       * @constructor
       */
       Date.prototype.Format = function (fmt) {
       var o = {
              "m+": this.getMonth() + 1, //月份
              "d+": this.getDate(), //日
              "h+": this.getHours(), //小时
              "m+": this.getMinutes(), //分
              "s+": this.getSeconds(), //秒
              "q+": Math.floor((this.getMonth() + 3) / 3), //季度
              "s": this.getMilliseconds()
              //毫秒
         };
         if (/(y+)/.test(fmt))
             fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
             for (var k in o)
                  if(new RegExp("(" + k + ")").test(fmt))
                     fmt=fmt.replace(RegExp.$1,(RegExp.$1.length==1)?(o[k]):(("00"+o[k]).substr((""+o[k]).length) ));
                     return fmt;
         };
         
         
         Date.prototype.formata = function(fmta) { 
             var o = { 
                "M+" : this.getMonth()+1,                 //月份 
                "d+" : this.getDate(),                    //日 
                "h+" : this.getHours(),                   //小时 
                "m+" : this.getMinutes(),                 //分 
                "s+" : this.getSeconds(),                 //秒 
                "q+" : Math.floor((this.getMonth()+3)/3), //季度 
                "S"  : this.getMilliseconds()             //毫秒 
            }; 
            if(/(y+)/.test(fmta)) {
                    fmta=fmta.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
            }
             for(var k in o) {
                if(new RegExp("("+ k +")").test(fmta)){
                     fmta = fmta.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
                 }
             }
            return fmta; 
        }