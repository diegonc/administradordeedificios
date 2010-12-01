
imgPath = "images/";
cssPath = "";
calDateFormat    = "DD/MM/yyyy";
topBackground    = "#F6EFF7";
bottomBackground = "#F6EFF7";
tableBGColor     = "#EAF3FD";
cellColor        = "#EAF3FD";
headingCellColor = "#818181";
headingTextColor = "#FFFFFF";
dateColor        = "#4B79AB";
focusColor       = "#FFC000";
hoverColor       = "#4B79AB";
headingFontStyle = "bold 8pt verdana, arial";
bottomBorder  = false;
tableBorder   = 0;
var isNav = false;
var isIE  = false;
var calDateField2 = '';
if (navigator.appName == "Netscape") {
    isNav = true;
    fontStyle        = "8pt verdana, arial";
}
else {
    isIE = true;
    fontStyle        = "7pt verdana, arial";
}
selectedLanguage = navigator.language;
buildCalParts();
allowPrevious  = false;
sinceDate = '';

function setDateField(dateFieldDia,dateFieldMes,dateFieldAnio) {
    calDateFieldDia = dateFieldDia;
    calDateFieldMes = dateFieldMes;
    calDateFieldAnio = dateFieldAnio;
    inDate=calDateFieldMes.value + '/' + calDateFieldDia.value + '/' + dateFieldAnio.value;
    setInitialDate();
    calDocTop    = buildTopCalFrame();
    calDocBottom = buildBottomCalFrame();
}

function setDateField2(dateField2) {
    calDateField2 = dateField2;    
}

function setDateFieldSince(dateFieldDia,dateFieldMes,dateFieldAnio,desde) {
    sinceDate = desde;
    calDateFieldDia = dateFieldDia;
    calDateFieldMes = dateFieldMes;
    calDateFieldAnio = dateFieldAnio;
    inDate=calDateFieldMes.value + '/' + calDateFieldDia.value + '/' + dateFieldAnio.value;
    setInitialDate();
    calDocTop    = buildTopCalFrame();
    calDocBottom = buildBottomCalFrame();
}

function setInitialDate() {
    calDate = new Date(inDate);
    if (isNaN(calDate)) {
        calDate = new Date(sinceDate);
        if (isNaN(calDate)) {
            calDate = new Date();
        }
    }
    calDay  = calDate.getDate();
    calMonth = calDate.getMonth();
    calDate.setDate(1);
}

function showCalendar(dateField) {
    setDateField(dateField);
    calDocFrameset = 
        "<HTML><HEAD><TITLE>Calendario Multilistas</TITLE></HEAD>\n" +
        "<FRAMESET ROWS='30,*' FRAMEBORDER='no' border=0 framespacing=0>\n" +
        "  <FRAME NAME='topCalFrame' SRC='javascript:parent.opener.calDocTop' SCROLLING='no' frameborder='no' marginwidth=0 marginheight=0>\n" +
        "  <FRAME NAME='bottomCalFrame' SRC='javascript:parent.opener.calDocBottom' SCROLLING='no' frameborder='no' marginwidth=0 marginheight=0>\n" +
        "</FRAMESET>\n";
    top.newWin = window.open("javascript:parent.opener.calDocFrameset", "calWin", winPrefs);
    top.newWin.focus();
}

function buildTopCalFrame() {
		var calDoc =
        "<HTML>" +
        "<HEAD>";
		if (cssPath!='')
		{
			calDoc += "<link rel='Stylesheet' href='" + cssPath + "bkb.css' type='text/css' media='screen'>";

		}
		calDoc += "</HEAD>" +
        "<BODY BGCOLOR='" + topBackground + "'leftmargin=0 ";
		if (cssPath!='')
		{
			calDoc += "style='background-color:#F6EFF7;' ";

		}
		calDoc += "topmargin=2 marginwidth=0 marginheight=0>" +
        "<FORM NAME='calControl' onSubmit='return false;'>" +
        "<CENTER>" +
        "<table border=0 cellspacing=0 cellpadding=1 align=center width=190 bgcolor=#94A6BE>" +
		"<tr><td>" +
        "<TABLE CELLPADDING=0 CELLSPACING=0 BORDER=0 align=center width=100% bgcolor=#EAF3FD>" +
        "<TR><TD align=center>";        
		calDoc +=         
				"<a href='javascript:;' onClick='parent.opener.setPreviousMonth()' NAME='previousMonth'>" +
				"<img src='" + imgPath + "ico_ant.gif' border=0 alt='Mes anterior' height=9 width=11></a>&nbsp;&nbsp;";

		calDoc += 
		getMonthSelect() +
        "&nbsp;<INPUT NAME='year'";
		if (cssPath!='')
		{
			calDoc += "class='txt11r555555' ";
		}
		calDoc += "VALUE='" + calDate.getFullYear() + "' TYPE=TEXT SIZE=4 MAXLENGTH=4 onChange='parent.opener.setYear()'>";
		calDoc +=		
				"&nbsp;&nbsp;<a href='javascript:;' NAME='nextMonth' onClick='parent.opener.setNextMonth()'>" +
				"<img src='" + imgPath + "ico_sig.gif' border=0 alt='Mes siguiente' height=9 width=11></a>";

		calDoc += 
        "</TD>" +
        "</TR>" +
        "</TABLE>" +
        "</TD>" +
        "</TR>" +
        "</TABLE>" +
        "</CENTER>" +
        "</FORM>" +
        "</BODY>" +
        "</HTML>";	
    return calDoc;
}

function buildBottomCalFrame() {       
    var calDoc = calendarBegin;
    month   = calDate.getMonth();
    year    = calDate.getFullYear();
    day     = calDay;                                  
    var i   = 0;
    var days = getDaysInMonth();
    if (day > days) {
        day = days;
    }
    var firstOfMonth = new Date (year, month, 1);
    var startingPos  = firstOfMonth.getDay();
    days += startingPos;
    var columnCount = 0;
    for (i = 0; i < startingPos; i++) {
        calDoc += blankCell;
	columnCount++;
    }
    var currentDay = 0;
    var currentMonth = 0;
    var dayType    = "weekday";
    for (i = startingPos; i < days; i++) {
	var paddingChar = "&nbsp;";
        if (i-startingPos+1 < 10) {
            padding = "&nbsp;&nbsp;";
        }
        else {
            padding = "&nbsp;";
        }
        currentDay = i-startingPos+1; 
        if ((currentDay == day) && (month == calMonth)) {
            dayType = "focusDay";
        }
        else {
            dayType = "weekDay";
        }

	// CHEQUEO LAS FECHAS PARA QUE NO PONGA LINKS EN DIAS ANTERIORES A HOY
    //checkDate = new Date();
    checkDate = new Date(sinceDate);
    if (isNaN(checkDate)) {
        checkDate = new Date();
    }
    var checkdate  = checkDate.getDate();
    var checkmonth = checkDate.getMonth();
    var checkyear  = checkDate.getFullYear();    
	var seldate = currentDay;
	var selmonth = month;
	var selyear = year;
    nResultado = checkyear * 10000 + ( checkmonth + 1 ) * 100 + checkdate;
    sResultado = selyear * 10000 + ( selmonth + 1 ) * 100 + seldate;
    if ((sResultado < nResultado) && !allowPrevious)
	// FECHA SIN LINK
        calDoc += "<TD class='heading2' align=center>" +
				  "<font face=verdana size=1>" +
                  padding + "<s>" + currentDay + "</s>" + paddingChar + "</font></TD>";
	else
	// FECHA CON LINK
        calDoc += "<TD class='heading2' align=center>" +
                  "<a class='" + dayType + "' href='javascript:parent.opener.returnDate(" + 
                  currentDay + ")'>" + padding + currentDay + paddingChar + "</a></TD>";
	    columnCount++;
        if (columnCount % 7 == 0) {
            calDoc += "</TR><TR>";
        }
    }
    for (i=days; i<42; i++)  {
        calDoc += blankCell;
	columnCount++;
        if (columnCount % 7 == 0) {
            calDoc += "</TR>";
            if (i<41) {
                calDoc += "<TR>";
            }
        }
    }
    calDoc += calendarEnd;
    return calDoc;
}

function writeCalendar() {
    calDocBottom = buildBottomCalFrame();
    top.newWin.frames['bottomCalFrame'].document.open();
    top.newWin.frames['bottomCalFrame'].document.write(calDocBottom);
    top.newWin.frames['bottomCalFrame'].document.close();
}

function setToday() {
    calDate = new Date();
    var month = calDate.getMonth();
    var year  = calDate.getFullYear();
    top.newWin.frames['topCalFrame'].document.calControl.month.selectedIndex = month;
    top.newWin.frames['topCalFrame'].document.calControl.year.value = year;
    writeCalendar();
}

function setYear() {
    var year  = top.newWin.frames['topCalFrame'].document.calControl.year.value;
    if (isFourDigitYear(year)) {
        calDate.setFullYear(year);
        writeCalendar();
    }
    else {
        top.newWin.frames['topCalFrame'].document.calControl.year.focus();
        top.newWin.frames['topCalFrame'].document.calControl.year.select();
    }
}

function setCurrentMonth() {
    var month = top.newWin.frames['topCalFrame'].document.calControl.month.selectedIndex;
    calDate.setMonth(month);
    writeCalendar();
}

function setPreviousYear() {
    var year  = top.newWin.frames['topCalFrame'].document.calControl.year.value;
    if (isFourDigitYear(year) && year > 1000) {
        year--;
        calDate.setFullYear(year);
        top.newWin.frames['topCalFrame'].document.calControl.year.value = year;
        writeCalendar();
    }
}

function setPreviousMonth() {
    var year  = top.newWin.frames['topCalFrame'].document.calControl.year.value;
    if (isFourDigitYear(year)) {
        var month = top.newWin.frames['topCalFrame'].document.calControl.month.selectedIndex;
        if (month == 0) {
            month = 11;
            if (year > 1000) {
                year--;
                calDate.setFullYear(year);
                top.newWin.frames['topCalFrame'].document.calControl.year.value = year;
            }
        }
        else {
            month--;
        }
        calDate.setMonth(month);
        top.newWin.frames['topCalFrame'].document.calControl.month.selectedIndex = month;
        writeCalendar();
    }
}

function setNextMonth() {
    var year = top.newWin.frames['topCalFrame'].document.calControl.year.value;
    if (isFourDigitYear(year)) {
        var month = top.newWin.frames['topCalFrame'].document.calControl.month.selectedIndex;
        if (month == 11) {
            month = 0;
            year++;
            calDate.setFullYear(year);
            top.newWin.frames['topCalFrame'].document.calControl.year.value = year;
        }
        else {
            month++;
        }
        calDate.setMonth(month);
        top.newWin.frames['topCalFrame'].document.calControl.month.selectedIndex = month;
        writeCalendar();
    }
}

function setNextYear() {
    var year  = top.newWin.frames['topCalFrame'].document.calControl.year.value;
    if (isFourDigitYear(year)) {
        year++;
        calDate.setFullYear(year);
        top.newWin.frames['topCalFrame'].document.calControl.year.value = year;
        writeCalendar();
    }
}

function getDaysInMonth()  {
    var days;
    var month = calDate.getMonth()+1;
    var year  = calDate.getFullYear();
    if (month==1 || month==3 || month==5 || month==7 || month==8 ||
        month==10 || month==12)  {
        days=31;
    }
    else if (month==4 || month==6 || month==9 || month==11) {
        days=30;
    }
    else if (month==2)  {
        if (isLeapYear(year)) {
            days=29;
        }
        else {
            days=28;
        }
    }
    return (days);
}

function isLeapYear (Year) {
    if (((Year % 4)==0) && ((Year % 100)!=0) || ((Year % 400)==0)) {
        return (true);
    }
    else {
        return (false);
    }
}

function isFourDigitYear(year) {
    if (year.length != 4) {
        top.newWin.frames['topCalFrame'].document.calControl.year.value = calDate.getFullYear();
        top.newWin.frames['topCalFrame'].document.calControl.year.select();
        top.newWin.frames['topCalFrame'].document.calControl.year.focus();
    }
    else {
        return true;
    }
}

function getMonthSelect() {
    monthArray = new Array('Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
                               'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre');

    var activeMonth = calDate.getMonth();
    monthSelect = "<SELECT NAME='month' ";
	if (cssPath!='')
	{
		monthSelect += "class='txt11r555555' ";
	}
	monthSelect += "onChange='parent.opener.setCurrentMonth()'>";
    for (i in monthArray) {
        if (i == activeMonth) {
            monthSelect += "<OPTION SELECTED>" + monthArray[i];
        }
        else {
            monthSelect += "<OPTION>" + monthArray[i];
        }
    }
    monthSelect += "</SELECT>";
    return monthSelect;
}

function createWeekdayList() {
    weekdayList  = new Array('Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado');
    weekdayArray = new Array('Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa');
   
    var weekdays = "<TR BGCOLOR='" + headingCellColor + "'>";
    for (i in weekdayArray) {
        weekdays += "<TD class='heading' align=center>" + weekdayArray[i] + "</TD>";
    }
    weekdays += "</TR>";
    return weekdays;
}

function buildCalParts() {
    weekdays = createWeekdayList();
    blankCell = "<TD align=center>&nbsp;&nbsp;&nbsp;</TD>";
    calendarBegin =
        "<HTML>" +
        "<HEAD>" +
        "<STYLE type='text/css'>" +
        "<!--" +
        "TD.heading2 { text-decoration: none; color:" + dateColor + "; font: " + fontStyle + "; }" +
        "TD.heading { text-decoration: none; color:" + headingTextColor + "; font: " + headingFontStyle + "; }" +
        "A.focusDay:link { color: " + focusColor + "; text-decoration: none; font: " + fontStyle + "; }" +
        "A.focusDay:hover { color: " + focusColor + "; text-decoration: none; font: " + fontStyle + "; }" +
        "A.focusDay:visited { color: " + focusColor + "; text-decoration: none; font: " + fontStyle + "; }" +
        "A.weekday:link { color: " + dateColor + "; text-decoration: none; font: " + fontStyle + "; }" +
        "A.weekday:hover { color: " + hoverColor + "; font: " + fontStyle + "; }" +
        "A.weekday:visited { color: " + dateColor + "; text-decoration: none; font: " + fontStyle + "; }" +
        "-->" +
        "</STYLE>" +
        "</HEAD>" +
        "<BODY BGCOLOR='" + bottomBackground + "' leftmargin=0 topmargin=0 marginwidth=0 marginheight=0>" +
        "<CENTER>" +
        "<table border=0 cellspacing=0 cellpadding=1 align=center width=190 bgcolor=#94A6BE>" +
		"<tr><td>";        
        // NAVIGATOR NEEDS A TABLE CONTAINER TO DISPLAY THE TABLE OUTLINES PROPERLY
        if (isNav) {
            calendarBegin += 
                "<TABLE CELLPADDING=0 CELLSPACING=0 BORDER=" + tableBorder + " ALIGN=CENTER width=100% BGCOLOR='" + tableBGColor + "'><TR><TD>";
        }
        calendarBegin +=
            "<TABLE CELLPADDING=0 CELLSPACING=0 BORDER=" + tableBorder + " ALIGN=CENTER width=100% BGCOLOR='" + tableBGColor + "'>" +
            weekdays +
            "<TR>";
	    calendarEnd = "";
        if (bottomBorder) {
            calendarEnd += "<TR></TR>";
        }
        // NAVIGATOR NEEDS A TABLE CONTAINER TO DISPLAY THE BORDERS PROPERLY
        if (isNav) {
            calendarEnd += "</TD></TR></TABLE>";
        }
        calendarEnd +=
			"<table border=0 cellspacing=0 cellpadding=0 align=center width=100% bgcolor='" + tableBGColor + "'>" +
			"<TR><TD>" +
			"<img src='" + imgPath + "p.gif' width=1 height=1>" +
			"</TD></TR>" +
			"<TR><TD ALIGN=CENTER>" +
			"<a href='javascript:window.top.close()'>" + 
			"<img src='" + imgPath + "bot_volver.gif' border=0></a>" +
			"</TD></TR>" +
			"<TR><TD>" +
			"<img src='" + imgPath + "p.gif' width=1 height=1>" +
			"</TD></TR>" +			
			"</TABLE>" +
			"</TD></TR>" +			
			"</TABLE>" +
			"</TD></TR>" +
			"</TABLE>" +			
            "</CENTER>" +
            "</BODY>" +
            "</HTML>";
}

function jsReplace(inString, find, replace) {
    var outString = "";
    if (!inString) {
        return "";
    }
    if (inString.indexOf(find) != -1) {
        t = inString.split(find);
        return (t.join(replace));
    }
    else {
        return inString;
    }
}

function doNothing() {
}

function makeTwoDigit(inValue) {
    var numVal = parseInt(inValue, 10);
    if (numVal < 10) {
        return("0" + numVal);
    }
    else {
        return numVal;
    }
}

function returnDate(inDay)
{
    calDate.setDate(inDay);
    var day           = calDate.getDate();
    var month         = calDate.getMonth()+1;
    var year          = calDate.getFullYear();
    var monthString   = monthArray[calDate.getMonth()];
    var monthAbbrev   = monthString.substring(0,3);
    var weekday       = weekdayList[calDate.getDay()];
    var weekdayAbbrev = weekday.substring(0,3);
    outDate = calDateFormat;
    if (calDateFormat.indexOf("DD") != -1) {
        day = makeTwoDigit(day);
        outDate = jsReplace(outDate, "DD", day);
    }
    else if (calDateFormat.indexOf("dd") != -1) {
        outDate = jsReplace(outDate, "dd", day);
    }
    if (calDateFormat.indexOf("MM") != -1) {
        month = makeTwoDigit(month);
        outDate = jsReplace(outDate, "MM", month);
    }
    else if (calDateFormat.indexOf("mm") != -1) {
        outDate = jsReplace(outDate, "mm", month);
    }
    if (calDateFormat.indexOf("yyyy") != -1) {
        outDate = jsReplace(outDate, "yyyy", year);
    }
    else if (calDateFormat.indexOf("yy") != -1) {
        var yearString = "" + year;
        var yearString = yearString.substring(2,4);
        outDate = jsReplace(outDate, "yy", yearString);
    }
    else if (calDateFormat.indexOf("YY") != -1) {
        outDate = jsReplace(outDate, "YY", year);
    }
    if (calDateFormat.indexOf("Month") != -1) {
        outDate = jsReplace(outDate, "Month", monthString);
    }
    else if (calDateFormat.indexOf("month") != -1) {
        outDate = jsReplace(outDate, "month", monthString.toLowerCase());
    }
    else if (calDateFormat.indexOf("MONTH") != -1) {
        outDate = jsReplace(outDate, "MONTH", monthString.toUpperCase());
    }
    if (calDateFormat.indexOf("Mon") != -1) {
        outDate = jsReplace(outDate, "Mon", monthAbbrev);
    }
    else if (calDateFormat.indexOf("mon") != -1) {
        outDate = jsReplace(outDate, "mon", monthAbbrev.toLowerCase());
    }
    else if (calDateFormat.indexOf("MON") != -1) {
        outDate = jsReplace(outDate, "MON", monthAbbrev.toUpperCase());
    }
    if (calDateFormat.indexOf("Weekday") != -1) {
        outDate = jsReplace(outDate, "Weekday", weekday);
    }
    else if (calDateFormat.indexOf("weekday") != -1) {
        outDate = jsReplace(outDate, "weekday", weekday.toLowerCase());
    }
    else if (calDateFormat.indexOf("WEEKDAY") != -1) {
        outDate = jsReplace(outDate, "WEEKDAY", weekday.toUpperCase());
    }
    if (calDateFormat.indexOf("Wkdy") != -1) {
        outDate = jsReplace(outDate, "Wkdy", weekdayAbbrev);
    }
    else if (calDateFormat.indexOf("wkdy") != -1) {
        outDate = jsReplace(outDate, "wkdy", weekdayAbbrev.toLowerCase());
    }
    else if (calDateFormat.indexOf("WKDY") != -1) {
        outDate = jsReplace(outDate, "WKDY", weekdayAbbrev.toUpperCase());
    }
	
	var arrayFecha = outDate.split('/');
	calDateFieldDia.value = arrayFecha[0];
	calDateFieldMes.value = arrayFecha[1];
	calDateFieldAnio.value = arrayFecha[2];
	
	if (calDateFieldDia.onchange)
		calDateFieldDia.onchange();
	if (calDateFieldMes.onchange)
		calDateFieldMes.onchange();
	if (calDateFieldAnio.onchange)
		calDateFieldAnio.onchange();

    if (calDateField2) {
	    calDateField2.value = outDate;
		}
    //calDateField.focus();
    top.newWin.close();
}

