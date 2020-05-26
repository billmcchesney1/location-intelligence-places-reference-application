$(document).ready(function() {
    $(".json").hide();
    $("#show-request").click(function() {
        $(".json").slideDown("1000");
        $("#response").hide();
        $("#request").show();
    });

    $("#show-response").click(function() {
        $(".json").slideDown("1000");
        $("#request").hide();
        $("#response").show();
    });

    $("#reload").click(function(){
        location.reload(true);
    });

    $("#close").click(function() {
        $(".json").slideUp("1000");
    });

    $("#reset").click(function() {
        location.reload(true);
    });
});

function toggleDropdown (e) {
    const _d = $(e.target).closest('.dropdown'),
        _m = $('.dropdown-menu', _d);
    setTimeout(function(){
        const shouldOpen = e.type !== 'click' && _d.is(':hover');
        _m.toggleClass('show', shouldOpen);
        _d.toggleClass('show', shouldOpen);
        $('[data-toggle="dropdown"]', _d).attr('aria-expanded', shouldOpen);
    }, e.type === 'mouseleave' ? 300 : 0);
}

$('body')
    .on('mouseenter mouseleave','.dropdown',toggleDropdown)
    .on('click', '.dropdown-menu a', toggleDropdown);