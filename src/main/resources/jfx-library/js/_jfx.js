/* _jfx.js */
$$.JFX = {
    Read : function(id) {
        var e = $$.Find(id);
        switch (e.tagName.toLowerCase()) {
            case "input":
                if (e.type === "radio" || e.type === "checkbox") {
                    return e.checked;
                }
                return e.value;
            case "select":
            case "textarea":
                return e.value;
            default:
                return null;
        }
    },
    Write : function(id, value) {
        var e = $$.Find(id);
        switch (e.tagName.toLowerCase()) {
            case "input":
                if (e.type === "radio" || e.type === "checkbox") {
                    e.checked = value === true;
                } else {
                    e.value = value;
                }
                break;
            case "select":
            case "textarea":
                e.value = value;
                break;
            case "button":
            case "div":
            case "label":
            case "span":
                $$.Set(e, value);
                break;
            default: 
                break;
        }
    }
};

$$.Init.Add(function() {
    var _preventSelection = function(event) { 
        var _hasInput = new Set([
            "input",
            "textarea",
            "select",
            "button"
            ]);
        
        if (!_hasInput.has(event.target.tagName.toLowerCase())) {
            $$.Events.Cancel(event);
        }
    };
    
    $$.Events.Add(document, "mousedown", _preventSelection);
    $$.Events.Add(document, "selectstart", _preventSelection);
    
    var _updateHtmlText = function(retry) {
		if (!$$.L10N.IsReady()) {
			setTimeout(function() { _updateHtmlText(retry - 1); }, 10)
			return;
		}
		
		$$.L10N.UpdateHtmlText();
	};
	_updateHtmlText(100);
    
    //var inputs = document.querySelectorAll("input[type=text], textarea");
    //$$.ForEach(inputs, function(input) {
    //    $$.Events.Add(input, "mouseenter", function(event) { $$.JFX.Views.Current.enableContextMenu(true); });
    //    $$.Events.Add(input, "mouseout", function(event) { $$.JFX.Views.Current.enableContextMenu(false); });
    //});
}, 0);
