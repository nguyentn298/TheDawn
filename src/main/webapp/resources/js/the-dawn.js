function waiting(isActive) {
	if (isActive) {
		var docHeight = $(window).height();
		var docWidth = $(window).width();
		var topP = (docHeight - 25) / 2;
		var leftP = (docWidth - 230) / 2;
		$("body").append("<div id='overlay'></div>");
		$("#overlay").height(docHeight).css({
			'cursor' : 'wait',
			'opacity' : 0.4,
			'position' : 'fixed',
			'background-repeat' : 'no-repeat',
			'background-position' : 'center',
			'top' : 0,
			'left' : 0,
			'background-color' : 'black',
			'width' : '100%',
			'z-index' : 5000
		});
		$("body").append("<div id='progressMessage'></div>");
		$("#progressMessage").css(
				{
					'cursor' : 'wait',
					'background-color' : '#ffffff',
					'background-image' : 'url(' + contextPath
							+ '/images/bg_white.png)',
					'background-repeat' : 'repeat-x',
					'min-width' : '230px',
					'color' : '#CC0000',
					'display' : 'block',
					'position' : 'fixed',
					'top' : topP + 'px',
					'left' : leftP + 'px',
					'padding' : '5px',
					'font-family' : 'Verdana,Arial,Helvetica,sans-serif',
					'height' : '25px',
					'z-index' : 5000,
				});
		$("#progressMessage").append("<span id='message'>Processing...</span>");
		$("#message").css({
			'font-size' : '18px',
		});
	} else {
		$("#overlay").remove();
		$("#progressMessage").remove();
	}
}

var DBUtil = {
	checkMongoDbConnection: function() {
		var isOk = false;
		DwrUtilitiesService.checkMongoDbConnection({
			async : false,
			callback :function(result) {
				if (!result) {
					showAlertPopup("The Mongo DB that gives IDEA its SLA information is not available. SLA filters will not work until this is corrected.");
				}
				isOk = result;
			}
		});
		return isOk;
	}
}

var ArrayUtil = {
	removeEmptyItemInArray : function (arrayInput){
		var rsArray = new Array();
		for (var i = 0; i < arrayInput.length; i++) {
			var item = $.trim(arrayInput[i]);
			if (item != "") {
				rsArray.push(item);
			}
		}
		return rsArray;
	}
}

var MultiselectUtil = {
	initMultiselectbox: function($element, dropdownMenuWidth, customClasses, afterSelectedCallbackFn) {
		if ($element.lenght == 0) {
			return;
		}
		$element.multiselect({
			header: false,
			selectedList: 1,
			minWidth: dropdownMenuWidth || 225,
			classes: customClasses || ""
		});
		var _self = this;
		_self.customMultiselect($element, afterSelectedCallbackFn);
	},
	initMultiselectWithFilter: function($element, dropdownMenuWidth, inputFilterWidth, customClasses, afterSelectedCallbackFn) {
		if ($element.lenght == 0) {
			return;
		}
		$element.multiselect({
			beforeopen: function() {
				$(".ui-multiselect-header").find(".ui-helper-reset").hide();
			},
			selectedList: 1,
			minWidth: dropdownMenuWidth || 225,
			classes: $element.attr("id") + "-filter" + " " + (customClasses || "")
		}).multiselectfilter({
			width : inputFilterWidth,
			placeholder : "",
			autoReset : true
		});
		var _self = this;
		_self.customMultiselect($element, afterSelectedCallbackFn);
	},
	customMultiselect: function($element, afterSelectedCallbackFn) {
	if ($element.lenght == 0) {
			return;
		}
		var $btnMultiselect = $element.next("button.ui-multiselect");
		$btnMultiselect.width($element.width());
		$btnMultiselect.removeClass("ui-widget ui-state-default ui-corner-all").addClass("textbox");
		$btnMultiselect.unbind("mouseenter");
		$btnMultiselect.unbind("mouseleave");
		$btnMultiselect.unbind("focus");
		$btnMultiselect.bind({
			click: function() {
				$(this).removeClass("ui-state-active");
			},
		});
		var _self = this;
		_self.initSelectAllOption($element, afterSelectedCallbackFn);
	},
	initSelectAllOption: function($multiselect, afterSelectedCallbackFn) {
		if ($multiselect.lenght == 0) {
			return;
		}
		var $selectAll = $multiselect.find("option:contains(All)");
		var selectAllValue = $selectAll.val();
		$multiselect.on("multiselectclick", function (event, ui) {
			if (ui.checked) {
				if (ui.value == selectAllValue) {
					$multiselect.multiselect("uncheckAll");
					$multiselect.multiselect('setChecked', selectAllValue);
				} else {
					var checkeds = $multiselect.multiselect("getChecked").map(function(){
						return this.value;
					}).get();
					// because the multiselectfilter does not see all items when filtering
					// then the uncheckAll method runs incorrectly.
					var $filter = $("." + $multiselect.attr("id") + "-filter").find(".ui-multiselect-filter");
					if ($filter) {
						//1. clear filter, save current filter value
						var $filterInput = $filter.find("input[type=text]");
						var filterValue = $filterInput.val();
						$filterInput.val("").trigger("keyup");
						//2. uncheck all items
						$multiselect.multiselect("uncheckAll");
						//3. filter again
						$filterInput.val(filterValue).trigger("keyup");
					} else {
						$multiselect.multiselect("uncheckAll");
					}
					for (var i in checkeds) {
						if (checkeds[i] != selectAllValue) {
							$multiselect.multiselect('setChecked', checkeds[i]);
						}
					}
				}
			}
			if (afterSelectedCallbackFn) {
				afterSelectedCallbackFn();
			}
		});
	},
	setCheckedMultiselect: function($multiselect, checkedItems) {
		if ($multiselect.length > 0 && checkedItems && checkedItems != "") {
			$multiselect.multiselect("uncheckAll");
			var arrCheckedItems = checkedItems.split(",");
			for (var i in arrCheckedItems) {
				$multiselect.multiselect('setChecked', arrCheckedItems[i]);
			}
		}
	},
	updateHiddenValueOfMultiselect: function($multiselect, $hidden) {
		if ($multiselect.length > 0 && $hidden.length > 0) {
			var value = $multiselect.multiselect("getChecked").map(function() {
				return this.value;
			}).get();
			$hidden.val(value);
		}
	},
}
