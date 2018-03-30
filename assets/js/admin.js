$(document).ready(function() {
    $('#fragment-users .table').on('click', '.fa-check', function() {
        let row = $(this).parent().parent().children();
        defaultPostRequest('api/user/update.php', {
            oldemail: row.eq(1).attr('data-email'),
            email: row.eq(1).children().first().val(),
            type: row.eq(3).children().first().val(),
            adminmode: true
        }, 'users', function() {
            window.location.href = 'admin.php?message=user_updated#users';
        });
    });

    $('#fragment-users .table').on('click', '.fa-trash-alt', function() {
        let row = $(this).parent().parent().children();
        showModal('Confirmation', '<p>This will delete the selected user. Are you sure you wish to delete him ?</p>', [{
            id: 'button-ok',
            class: 'btn-primary',
            text: 'OK',
            callback: function() {
                closeModal();
                loaderFadeIn();
                $.post('api/user/delete.php', {username: row.eq(0).children().first().val()}, function() {
                    loaderFadeOut();
                    window.location.href = 'admin.php?message=user_updated#users';
                }, 'json');
            }
        }]);
    });

    $('#fragment-ads .table').on('click', '.fa-check', function() {
        let row = $(this).parent().parent().children();
        defaultPostRequest('api/ad/update.php', {
            oldtype: row.eq(2).attr('data-type'),
            oldtitle: row.eq(0).attr('data-title'),
            username: row.eq(5).attr('data-username'),
            title: row.eq(0).children().first().val(),
            message: row.eq(1).children().first().val(),
            type: row.eq(2).children().first().val(),
            interval: row.eq(3).children().first().val(),
            expiration: new Date(row.eq(4).children().first().val()).getTime() / 1000
        }, 'ads', null);
    });
});

$(document).on('fragmentChanged', function (event, fragment) {
    if(fragment == 'ads') {
        makeRequest('ads', {
            'url': 'api/ad/list.php',
            'data': null
        }, {
            'buttons': '<i class="fas fa-edit"></i> <i class="fas fa-trash-alt"></i>',
            'handlingLength': 6,
            'dataHandler': ADS_DATA_HANDLER
        }, true);
        return;
    }

    if(fragment == 'users') {
        makeRequest('users', {
            'url': 'api/user/list.php',
            'data': null
        }, {
            'buttons': '<i class="fas fa-edit"></i> <i class="fas fa-trash-alt"></i>',
            'handlingLength': 6,
            'dataHandler': function(data, i) {
                switch(i) {
                    case 0:
                        return ['username', data['username'], 'none'];
                    case 1:
                        return ['email', data['email'], 'email'];
                    case 2:
                        return ['verified', data['verified'] == 0 ? 'No' : 'Yes', 'none'];
                    case 3:
                        return ['type', null, 'select', ['Admin', 'Publisher']];
                    case 4:
                        return ['last_login', new Date(formatDate(new Date(data['last_login'] * 1000))).toLocaleDateString(), 'none'];
                    case 5:
                        return ['registered', new Date(formatDate(new Date(data['registered'] * 1000))).toLocaleDateString(), 'none'];
                }
            }
        }, true);
    }
});

