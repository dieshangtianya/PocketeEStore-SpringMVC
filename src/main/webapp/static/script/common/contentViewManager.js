var ContentViewManager = (function () {

        var resourceManger = new ResourceManager();

        var embeddedHtmlContent = function (viewName, $targetElement, htmlContent) {
            var $contentElement = $(htmlContent);
            var $container = $('<div></div>');
            $container.append($contentElement);
            $container.contents().filter(function () {
                return this.nodeType == 8;
            }).remove();
            var $scriptElements = $container.find('script').remove();
            var $linkStyleElements = $container.find('link').remove();

            resourceManger.addViewResources(viewName, $scriptElements, $linkStyleElements);

            $targetElement.get(0).innerHTML = $container.html();
        };

        function ResourceManager() {
            var scriptResourceMap = new webUtil.DataMap();
            var linkStyleResourceMap = new webUtil.DataMap();

            var runScript = function (scriptItem) {
                var deferred = $.Deferred();
                var script = document.createElement('script');
                if (scriptItem.src) {
                    script.src = scriptItem.src;
                } else {
                    script.innerHTML = scriptItem.innerHTML;
                }
                script.onload = function () {
                    deferred.resolve();
                };
                script.onerror = function (ev) {
                    deferred.reject();
                }
                document.body.appendChild(script);
                if (!scriptItem.src) {
                    deferred.resolve();
                }
                return deferred.promise();
            };

            var runStyle = function (styleItem) {
                var deferred = $.Deferred();
                var linkStyle = document.createElement('link');
                linkStyle.setAttribute("rel", "stylesheet");
                linkStyle.setAttribute("type", "text/css");
                linkStyle.setAttribute("href", styleItem.href);

                linkStyle.onload = function () {
                    deferred.resolve();
                };
                linkStyle.onerror = function () {
                    deferred.reject();
                }
                document.head.appendChild(linkStyle);
                return deferred.promise();
            }

            var batchRunTaskAsync = function (task, collections) {
                var deferred = $.Deferred();
                deferred.resolve();

                return Array.prototype.slice.apply(collections)
                    .reduce(function (chain, item) {
                            return chain.then(function () {
                                return task(item);
                            })
                        }, deferred.promise()
                    );
            };

            var batchRunScriptAndStyles = function (styleItems, scriptItems) {
                var deferred = $.Deferred();
                deferred.resolve();

                deferred.promise()
                    .then(function () {
                        if (styleItems && styleItems.length > 0) {
                            return batchRunTaskAsync(runStyle, styleItems);
                        }
                    })
                    .then(function () {
                        if (scriptItems && scriptItems.length > 0) {
                            return batchRunTaskAsync(runScript, scriptItems)
                        }
                    });
            };

            var batchRemoveScriptAndStyles = function (styleItems, scriptItems) {
                var activeScriptElements = $('script');
                var activeStyleElements = $('link');
                var i, j;

                if (styleItems && styleItems.length > 0) {
                    for (i = 0; i < styleItems.length; i += 1) {
                        for (j = 0; j < activeStyleElements.length; j += 1) {
                            if (styleItems[i].href === activeStyleElements[j].href) {
                                activeStyleElements[j].remove();
                                break;
                            }
                        }
                    }
                }

                if (scriptItems && scriptItems.length > 0) {
                    for (i = 0; i < scriptItems.length; i += 1) {
                        for (j = 0; j < activeScriptElements.length; j += 1) {
                            if (scriptItems[i].src === activeScriptElements[j].src) {
                                activeScriptElements[j].remove();
                                break;
                            }
                        }
                    }
                }
            }

            this.addViewResources = function (viewName, $scriptResourceElements, $linkStyleElements) {
                scriptResourceMap.add(viewName, $scriptResourceElements);
                linkStyleResourceMap.add(viewName, $linkStyleElements);
                batchRunScriptAndStyles($linkStyleElements, $scriptResourceElements);
            };

            this.removeResource = function (viewName) {
                var scriptResources = scriptResourceMap.get(viewName);
                var styleResources = linkStyleResourceMap.get(viewName);
                scriptResourceMap.remove(viewName);
                linkStyleResourceMap.remove(viewName);
                batchRemoveScriptAndStyles(styleResources, scriptResources);
            }
        }

        var contentViewManager = {};

        var currentViewUrl = '';

        contentViewManager.loadContentViewTo = function ($targetElement, viewUrl) {
            if (currentViewUrl) {
                $targetElement.empty();
                resourceManger.removeResource(currentViewUrl);
            }
            $.get(viewUrl)
                .then(function (htmlContent) {
                    currentViewUrl = viewUrl;
                    embeddedHtmlContent(viewUrl, $targetElement, htmlContent);
                });
        };

        return contentViewManager;

    }

)();