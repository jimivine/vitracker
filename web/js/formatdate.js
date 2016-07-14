function formatDDMMYY()
{
    var n = new Date().getDate();
    var d = new Date();
    var months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    var days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
    return days[d.getDay()] + ', ' + months[d.getMonth()] + ' ' + ' ' + n + ((Math.floor(n / 10) === 1) ? "th" : ["th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"][n % 10]) + ' ' + d.getFullYear();
}

function formatAMPM()
{
    var d = new Date();
    var minutes = d.getMinutes() < 10 ? 0 : d.getMinutes();
    var hours = d.getHours();
    var ampm = d.getHours() >= 12 ? 'pm' : 'am';
    return hours + ':' + minutes + ampm;
}


