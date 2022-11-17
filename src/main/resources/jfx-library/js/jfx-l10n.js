/* jfx-l10n.js */
$$.L10N = {
	GetText : function(id) {
		if ($$.L10N.IsReady() !== true) {
			return "L10N not gesitered";
		}
		return L10N.getTextNoVars(id);
	},
	IsReady : function() {
		return typeof(L10N) === "object";
	},
	UpdateHtmlText : function() {
		var html = document.querySelector('html');
		var walker = document.createTreeWalker(html, NodeFilter.SHOW_TEXT);
		var node;
		var text;
		var matcher;
		var exp = new RegExp("(%([^% '\"<>]+)%)");
		while (node = walker.nextNode()) {
			text = node.nodeValue;
			do {
				matcher = exp.exec(text);
				if (matcher) {
					text = text.replace(matcher[1], $$.L10N.GetText(matcher[2]));
				}
			} while (matcher);
			node.nodeValue = text;
		}
	}
};
