(function () {

    var menuData = [];

    $(document).ready(function () {
        pageLoad()
    });

    function pageLoad() {
        loadMenus();
    }

    function loadMenus() {
        return $.get(APPHelper.APIMap.SYSTEM_MENUS)
            .then(function (res) {
                if (res.status === 0) {
                    buildMenus(res.result.data);
                    menuData = res.result.data;
                    return res.result.data;
                }
                return [];
            })
            .then(function (menuData) {
                if (menuData.length > 0) {
                    var menuItemContainer = $('.menu').children('li').get(0);
                    var $menuItemElement = $(menuItemContainer).children('.menu-item');
                    changeMainView(menuData[0], $menuItemElement);
                }
            });
    }

    function menuItemClick(menuItem, menuItemElement) {
        if (menuItem.items && menuItem.items.length > 0) {
            menuItemElement.parents('li').children('.child-menu').toggle();
            menuItemElement.children('.toggle-expand').toggleClass("icon-up-circle");
        } else {
            changeMainView(menuItem, menuItemElement);
        }
    }

    function changeMainView(menuItem, menuItemElement) {
        // change menu style
        $('#drawer-panel .menu-item').removeClass('active');
        menuItemElement.addClass('active');
        // change breadCrumb
        changeBreadCrumb(menuItem, menuItemElement)

        // load view
        changeMainViewHTML(menuItem);
        //changeMainViewWithFrame(menuItem);
        //changeMainViewWithRedirect(menuItem);
    }

    function changeMainViewHTML(menuItem) {
        var mainContent = $('#main-content');
        if (menuItem.menuUrl) {
            htmlHelper.loadContentViewTo(mainContent, menuItem.menuUrl);
        }
    }

    function changeMainViewWithFrame(menuItem) {
        var appIFrame = $('#mainIFrame');
        appIFrame.attr('src', menuItem.menuUrl);
    }

    function changeMainViewWithRedirect(menuItem) {
        //redirect directly
        window.location.href = menuItem.menuUrl;
    }

    function changeBreadCrumb(currentMenuItem, currentMenuItemElement) {
        // because here the menu depth only 2, so we just get the parent menu item to display the bread crumb
        var parentItem = getParentMenuItem(currentMenuItem);
        var breadCrumbString = '';
        if (parentItem) {
            //it is not necessary to redirect the url here.
            breadCrumbString = '<a>' + parentItem.menuText + '</a><span class="bread-separator">&gt;</span>';
        }
        breadCrumbString += '<span class="current">' + currentMenuItem.menuText + '</span>';

        var breadCrumb = $(breadCrumbString);

        $('#bread-crumb').html(breadCrumb);
    }

    function getParentMenuItem(targetItem) {
        for (var i = 0; i < menuData.length; i++) {
            var parentItem = findParentItem(menuData[i], targetItem);
            if (parentItem) {
                return parentItem;
            }
        }
        return null;
    }

    function findParentItem(currentItem, targetItem) {
        if (currentItem.items && currentItem.items.length > 0) {
            for (var index = 0; index < currentItem.items.length; index++) {
                if (currentItem.items[index] === targetItem) {
                    return currentItem;
                }
            }
        }
        return null;
    }

    function buildMenus(menuItems) {
        var menuListElement = $('<ul class="menu"></ul>');
        for (var i = 0; i < menuItems.length; i++) {
            var menuItemElement = createMenuItem(menuItems[i]);
            menuListElement.append(menuItemElement);
        }
        $('#drawer-panel').append(menuListElement);
    }

    function createMenuItem(menuItem) {
        var menuItemContainer = $('<li></li>');
        var iconElement = createMenuIcon(menuItem);
        var expandIconElement = createMenuItemExpandIcon(menuItem);
        var menuItemElement = $('<div class="menu-item"></div>');
        var menuTextElement = createMenuTextElement(menuItem);
        menuItemElement.append(iconElement);
        menuItemElement.append(menuTextElement);
        if (expandIconElement) {
            menuItemElement.append(expandIconElement);
        }
        menuItemElement.on('click', function () {
            menuItemClick(menuItem, menuItemElement);
        });
        menuItemContainer.append(menuItemElement);
        if (menuItem.items && menuItem.items.length > 0) {
            var childrenMenuContainer = $('<ul class="child-menu" style="display: none"></ul>');
            for (var i = 0; i < menuItem.items.length; i++) {
                var childItem = createChildMenuItem(menuItem.items[i]);
                childrenMenuContainer.append(childItem);
            }
            menuItemContainer.append(childrenMenuContainer);
        }
        return menuItemContainer;
    }

    function createMenuIcon(menuItem, custom_icon) {
        var iconClass = 'icon-project';
        if (menuItem.menuUrl === '/') {
            iconClass = 'icon-home';
        }
        if (custom_icon) {
            iconClass = custom_icon;
        }
        return $('<i class="icon iconfont ' + iconClass + '"></i>');
    }

    function createMenuItemExpandIcon(menuItem) {
        var hasChildren = menuItem.items && menuItem.items.length > 0;
        if (hasChildren) {
            return $('<i class="icon iconfont icon-down-circle toggle-expand"></i>')
        }
        return null;
    }

    function createChildMenuItem(menuItem) {
        var menuItemContainer = $('<li></li>');
        var iconElement = createMenuIcon(menuItem, 'icon-activity');
        var menuItemElement = $('<div class="menu-item child-item"></div>');
        var menuTextElement = createMenuTextElement(menuItem);
        menuItemElement.append(iconElement);
        menuItemElement.append(menuTextElement);
        menuItemContainer.append(menuItemElement);

        menuItemElement.on('click', function () {
            menuItemClick(menuItem, menuItemElement);
        });
        return menuItemContainer;
    }

    function createMenuTextElement(menuItem) {
        var menuTextElement = $('<span class="menu-text">' + menuItem.menuText + '</span>');
        if (menuItem.menuUrl) {
            menuTextElement = $('<a class="menu-text">' + menuItem.menuText + '</a>');
        }
        return menuTextElement;
    }
})();