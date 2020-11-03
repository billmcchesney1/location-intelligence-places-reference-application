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

const View = (function($, document) {
  'use strict';

  const services = [
    'hasNfc',
    'hasCashBack',
    'hasPayAtThePump'
  ];
  const servicesDescription = [
    'Has NFC',
    'Has Cashback',
    'Has Pay at The Pump'
  ];

  return function(config) {
    let placeTemplate;
    let serviceItemTemplate;

    let maps;
    let onMapInitializedCallback;

    function showLoadingIndicator(textToShow) {
      $('#places-list-loading')
        .removeClass('hide')
        .html(textToShow + '&hellip;');
    }

    function hideLoadingIndicator() {
      $('#places-list-loading')
        .html('')
        .addClass('hide');
    }

    function createPlaceServiceItem(text) {
      return serviceItemTemplate
        .clone()
        .removeAttr('id')
        .removeClass('hide')
        .html(text);

    }

    function panToMarker(elem) {
      const place = elem.data();
      maps.panTo({lat: place.latitude, lng: place.longitude});
    }

    function expandPlaceNode(elem, scrollIntoView) {
      $('.places-list-item-details')
        .slideUp()
        .parents('.places-list-item')
        .removeClass('places-list-item-selected');

      elem
        .addClass('places-list-item-selected')
        .find('.places-list-item-details')
        .slideDown(function() {
          if (scrollIntoView) {
            $('.places-list-container').animate({
              scrollTop: elem.offset().top - elem.parent().offset().top + elem.parent().scrollTop()
            });
          }
        });
    }

    const matchIndustry = (allIndustryCodes, industryCode) => {
      let industryReturn = industryCode;

      const fullIndustry = allIndustryCodes.find(industry => industry.industry === industryCode);

      if (fullIndustry) {
        industryReturn = fullIndustry.industryName
      }

      return industryReturn;
    };

    const matchCategory = (allCategoryCodes, categoryCode) => {
      let categoryReturn = categoryCode;

      const fullCategory = allCategoryCodes.find(merchantCategoryCode => merchantCategoryCode.merchantCategoryCode === categoryCode);

      if (fullCategory) {
        categoryReturn = fullCategory.merchantCategoryName
      }

      return categoryReturn;
    };

    return {
      getMaps: function() {
        return maps;
      },

      initMap: function() {
        maps = new Maps(document.getElementById('map'),
          config.zoomLevel,
          config.defaultLocation,
          function() {
            $('#places-list-wrapper').removeClass('hide');
          }
        );

        placeTemplate = $('#places-list-item-template');
        serviceItemTemplate = $('#places-list-item-details-service-template');

        if (onMapInitializedCallback) {
          onMapInitializedCallback();
        }
      },

      onMapInitialized: function(callback) {
        onMapInitializedCallback = callback;
      },

      renderCurrentLocation: function(location) {
        showLoadingIndicator('Fetching locations Near O\'Fallon, MO');
        maps.setCurrentLocationMarker(location);
        maps.panTo(location);
      },

      addCenterChangedHandler: function(callback) {
        maps.addCenterChangedHandler(callback);
      },

      renderPlace: function(place, industryCodes, mccCodes) {
        hideLoadingIndicator();

        const placeListElem = $('#place-list');

        const distance = DistanceUtils.getDistanceFromLatLon(config.defaultLocation.lat, config.defaultLocation.lng, place.latitude, place.longitude, config.distanceUnit);

        // render Place data into DOM
        const placeItem = placeTemplate
          .clone()
          .removeAttr('id')
          .removeClass('hide');

        // store place data into node
        placeItem.data(place);

        // render place information
        placeItem.find('.places-list-item-name')
          .html(place.cleansedMerchantName);

        placeItem.find('.places-list-item-distance')
          .html(distance.toFixed(2) + ' miles');

        placeItem.find('.places-list-item-industry')
            .html("Industry: " + matchIndustry(industryCodes, place.industry) );

        placeItem.find('.places-list-item-category')
            .html("Category: " + matchCategory(mccCodes, place.mccCode) );

        placeItem.find('.places-list-item-address-line1')
          .html(place.cleansedStreetAddress);

        placeItem.find('.places-list-item-address-city')
          .html(place.cleansedCityName + ", " + place.cleansedCountryCode + " " + place.cleansedPostalCode);

        // add services information
        let serviceItemHolder = placeItem.find('.places-list-item-details-services-list');
        services.forEach(function(service, index) {
          if (place[service] === true) {
            const serviceItem = createPlaceServiceItem(servicesDescription[index]);

            serviceItemHolder.append(serviceItem);
          }
        });
        if (!serviceItemHolder.children().length) {
          serviceItemHolder.append(createPlaceServiceItem('None'));
        }

        // add click listener to expand the node to show details and
        // to pan to appropriate marker on the map
        placeItem.find('.places-list-item-header-container')
          .click(function(event) {
            const target = $(event.currentTarget);
            const node = target.parents('.places-list-item');
              expandPlaceNode(node);
              panToMarker(node);
          });

        placeListElem.append(placeItem);

        const position = {
          lat: place.latitude,
          lng: place.longitude,
        };

        // create google maps marker
        maps.addMarker(position, function() {
          maps.panTo(this.getPosition());

          // scroll to the dom node
          expandPlaceNode(placeItem, true);
        });
      }
    };
  };
})(window.jQuery, window.document);
