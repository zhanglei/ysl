var parseURL = require('url').parse;
//����http�����method���ֱ𱣴�route����
var routes = {get:[], post:[], head:[], put:[], delete:[]};
/**
* ע��route����
* ʾ����
* route.map({
*     method:'post',
*     url: /\/blog\/post\/(\d+)\/?$/i,
*     controller: 'blog',
*     action: 'showBlogPost'
* })
*/
exports.map = function(dict){
    if(dict && dict.url && dict.controller){
        var method = dict.method ? dict.method.toLowerCase() : 'get';
        routes[method].push({
            u: dict.url, //urlƥ������
            c: dict.controller,
            a: dict.action || 'index'
        });
    }
};
exports.getAction = function(url, method){
    var r = {controller:null, action:null, args:null},
        method = method ? method.toLowerCase() : 'get',
        // url: /blog/index?page=1 ,��pathnameΪ: /blog/index
        pathname = parseURL(url).pathname;
    var m_routes = routes[method];
    for(var i in m_routes){
        //����ƥ��
        r.args = m_routes[i].u.exec(pathname);
        if(r.args){
            r.controller = m_routes[i].c;
            r.action = m_routes[i].a;
            r.args.shift(); //��һ��ֵΪƥ�䵽������url��ȥ��
            break;
        }
    }
    //���ƥ�䵽route��r����� {controller:'blog', action:'index', args:['1']}
    return r;
};