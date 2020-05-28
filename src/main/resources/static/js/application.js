/*
*  Copyright (c) 2020 Mastercard
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

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