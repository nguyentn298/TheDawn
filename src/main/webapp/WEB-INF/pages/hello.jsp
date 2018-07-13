<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

<script>
jQuery(document).ready(function($) {
	showMeYourCookies('When index document is ready');

	$.ajax({
		type: 'GET',
		url: '/TheDawn/rest/testService'

	}).done(function (data, textStatus, jqXHR) {
		showMeYourJqXHR('When GET /rest/hello is done', jqXHR);
		showMeYourCookies('When GET /rest/hello is done');

		var csrfToken = jqXHR.getResponseHeader('X-CSRF-TOKEN');
		if (csrfToken) {
			var cookie = JSON.parse(myCookie('helloween'));
			cookie.csrf = csrfToken;
			myCookie('helloween', JSON.stringify(cookie));
		}

		$('#helloweenMessage').html(data.message);

	}).fail(function (jqXHR, textStatus, errorThrown) {
		showMeYourJqXHR('When GET /rest/hello fails', jqXHR);
		showMeYourCookies('When GET /rest/hello fails');

		if (jqXHR.status === 401) { // HTTP Status 401: Unauthorized
			var cookie = JSON.stringify({method: 'GET', url: '/', csrf: jqXHR.getResponseHeader('X-CSRF-TOKEN')});
			myCookie('helloween', cookie);

			window.location = '/login.html';

		} else {
			console.error('Houston, we have a problem...');
		}
	});

	$('#postButton').on('click', function () {
		event.preventDefault();

		showMeYourCookies('When postButton is clicked');

		var cookie = JSON.parse(myCookie('helloween'));
		$.ajax({
			data: {},
			headers: {'X-CSRF-TOKEN': cookie.csrf},
			timeout: 1000,
			type: 'POST',
			url: '/TheDawn/rest/testService2'
			
		}).done(function(data, textStatus, jqXHR) {
			showMeYourJqXHR('When POST /rest/hellopost is done', jqXHR);
			showMeYourCookies('When POST /rest/hellopost is done');

			console.info("POST succeeded!!!");

		}).fail(function(jqXHR, textStatus, errorThrown) {
			showMeYourJqXHR('When POST /rest/hellopost fails', jqXHR);
			showMeYourCookies('When POST /rest/hellopost fails');

			console.error('Problems when posting...');
		});
	});

	$('#logoutButton').on('click', function (event) {
		event.preventDefault();

		showMeYourCookies('When logoutButton is clicked');

		var cookie = JSON.parse(myCookie('helloween'));
		$.ajax({
			data: {},
			headers: {'X-CSRF-TOKEN': cookie.csrf},
			timeout: 1000,
			type: 'POST',
			url: '/logout'

		}).done(function(data, textStatus, jqXHR) {
			showMeYourJqXHR('When POST /logout is done', jqXHR);
			showMeYourCookies('When POST /logout is done');

			console.info('congratulations, you have logged out!');

			window.location = '/';

		}).fail(function(jqXHR, textStatus, errorThrown) {
			showMeYourJqXHR('When POST /logout fails', jqXHR);
			showMeYourCookies('When POST /logout fails');

			console.error('Help! I cannot get out of here!');
		});
	});
	

	// Extract some information from the cookies
	function showMeYourCookies(title) {
		var jsessionid = myCookie('JSESSIONID');
		if (jsessionid) {
			console.log('>>>>> ' + title + ' JSESSIONID cookie = ' + jsessionid);
		} else {
			console.warn('>>>>> ' + title + ' no JSESSIONID cookie was found');
		}

		var restsecurity = myCookie('helloween');
		if (restsecurity) {
			restsecurity = JSON.parse(restsecurity);
			console.log('>>>>> ' + title + ' CSRF token in cookie = ' + restsecurity.csrf);
		} else {
			console.warn('>>>>> ' + title + ' no restsecurity cookie was found');
		}
	}

	// Extract some info from the returned jqXHR
	function showMeYourJqXHR(title, jqXHR) {
		if (jqXHR) {
			console.log('>>>>> ' + title + ' jqXHR X-CSRF-TOKEN = ' + jqXHR.getResponseHeader('X-CSRF-TOKEN'));
		} else {
			console.error('>>>>> ' + title + ' no jqXHR is defined... That\'s not normal at all...');
		}
	}
	
	function myCookie(key, value, options) {

        // key and at least value given, set cookie...
        if (arguments.length > 1 && (!/Object/.test(Object.prototype.toString.call(value)) || value === null || value === undefined)) {
            options = $.extend({}, options);

            if (value === null || value === undefined) {
                options.expires = -1;
            }

            if (typeof options.expires === 'number') {
                var days = options.expires, t = options.expires = new Date();
                t.setDate(t.getDate() + days);
            }

            value = String(value);
			console.log('Dantes value ' + value);
            return (document.cookie = [
                encodeURIComponent(key), '=', options.raw ? value : encodeURIComponent(value),
                options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
                options.path    ? '; path=' + options.path : '',
                options.domain  ? '; domain=' + options.domain : '',
                options.secure  ? '; secure' : ''
            ].join(''));
        }

        // key and possibly options given, get cookie...
        options = value || {};
        var decode = options.raw ? function(s) { return s; } : decodeURIComponent;

        var pairs = document.cookie.split('; ');
        for (var i = 0, pair; pair = pairs[i] && pairs[i].split('='); i++) {
            if (decode(pair[0]) === key) return decode(pair[1] || ''); // IE saves cookies with empty string as "c; ", e.g. without "=" as opposed to EOMB, thus pair[1] may be undefined
        }
        return null;
    };
});

</script>

</head>
<body>
	<h1>Title : ${title}</h1>
	<h1>Message : ${message}</h1>

	<h1>
		<c:if test="${not empty adm}">
		Admin message: ${adm}
	</c:if>
	</h1>
	<div>Test</div>
	<div>Test</div>
	<button id="postButton" type="button">POST something...</button>

</body>
</html>