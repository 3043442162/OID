const showXml = (str)=> {
    var that = this;
    var text = str;

    //去掉多余的空格
    text =
        "\n" +
        text
            .replace(/(<\w+)(\s.*?>)/g, function($0, name, props) {
                return name + " " + props.replace(/\s+(\w+=)/g, " $1");
            })
            .replace(/>\s*?</g, ">\n<");

    //把注释编码
    text = text
        .replace(/\n/g, "\r")
        .replace(/<!--(.+?)-->/g, function($0, text) {
            var ret = "<!--" + escape(text) + "-->";
            return ret;
        })
        .replace(/\r/g, "\n");

    //调整格式
    var rgx = /\n(<(([^\?]).+?)(?:\s|\s*?>|\s*?(\/)>)(?:.*?(?:(?:(\/)>)|(?:<(\/)\2>)))?)/gm;
    var nodeStack = [];
    var output = text.replace(rgx, function(
        $0,
        all,
        name,
        isBegin,
        isCloseFull1,
        isCloseFull2,
        isFull1,
        isFull2
    ) {
        var isClosed =
            isCloseFull1 == "/" ||
            isCloseFull2 == "/" ||
            isFull1 == "/" ||
            isFull2 == "/";
        var prefix = "";
        if (isBegin == "!") {
            prefix = that.getPrefix(nodeStack.length);
        } else {
            if (isBegin != "/") {
                prefix = that.getPrefix(nodeStack.length);
                if (!isClosed) {
                    nodeStack.push(name);
                }
            } else {
                nodeStack.pop();
                prefix = that.getPrefix(nodeStack.length);
            }
        }
        var ret = "\n" + prefix + all;
        return ret;
    });

    var prefixSpace = -1;
    var outputText = output.substring(1);

    //把注释还原并解码，调格式
    outputText = outputText
        .replace(/\n/g, "\r")
        .replace(/(\s*)<!--(.+?)-->/g, function($0, prefix, text) {
            if (prefix.charAt(0) == "\r") prefix = prefix.substring(1);
            text = unescape(text).replace(/\r/g, "\n");
            var ret =
                "\n" + prefix + "<!--" + text.replace(/^\s*/gm, prefix) + "-->";
            return ret;
        });
    //alert(outputText);

    outputText = outputText.replace(/\s+$/g, "").replace(/\r/g, "\r\n");

    return outputText;
}
export default showXml()
