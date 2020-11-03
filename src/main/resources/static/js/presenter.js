/*
 * Copyright 2020 MasterCard International.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials
 * provided with the distribution.
 * Neither the name of the MasterCard International Incorporated nor the names of its
 * contributors may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 * IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 */

const Presenter = (function($, navigator, API) {
  'use strict';

  return function(view, config) {
    let loadingInProgress = false, maxItemsReached = false;



    const currentLocation = config.defaultLocation;

    let pageOffset = 0;
    const pageLength = 25;

    function init() {
      view.onMapInitialized(onMapInitialized);
    }

    function onMapInitialized() {
      fetchPlaces();
      view.renderCurrentLocation(currentLocation);
      view.addCenterChangedHandler(onCenterChanged);
    }

    function onCenterChanged() {
      fetchPlaces();
    }

    function fetchPlaces() {
      if (loadingInProgress || maxItemsReached) {
        return;
      }
      loadingInProgress = true;

      Promise.all([API.getIndustryCodes(), API.getMccCodes(),
                              API.getPlaces(currentLocation.lat, currentLocation.lng, config.distanceUnit,
                                  config.country, pageLength, pageOffset)])
        .then((results) => {
          const [industryCodesResponse, mccCodesResponse, placesResponse] = results;
          pageOffset += pageLength;
          if (placesResponse.total <= pageOffset) {
            maxItemsReached = true;
          }

          placesResponse.items.forEach(place => {
            view.renderPlace(place, industryCodesResponse.items, mccCodesResponse.items);
          });
        })
        .catch(err => {
          console.error("There was an issue retrieving places: ", err.responseText)
        })
        .finally(() => {
          loadingInProgress = false;
        });
    }

    init();

    return {

    };
  };
})(window.jQuery, window.navigator, API);
