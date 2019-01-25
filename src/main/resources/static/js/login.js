$(document).ready(function () {
    $("#readme").change(function () {
        if ($(this).is(":checked")) {
            $("#submit").removeAttr("disabled", "disabled");
        } else {
            $("#submit").attr("disabled", "disabled");
        }
    });
    $('form').validator({
        onValid: function (validity) {
            $(validity.field).closest('.alert').find('.am-alert').hide();
        },

        onInValid: function (validity) {
            var $field = $(validity.field);
            var $group = $field.closest('.alert');
            var $alert = $group.find('.am-alert');
            // 使用自定义的提示信息 或 插件内置的提示信息
            var msg = $field.data('validationMessage') || this.getValidationMessage(validity);
            if (!$alert.length) {
                $alert = $('<div class="log-alert am-alert am-alert-danger am-radius"></div>').hide().appendTo($group);
            }

            $alert.html(msg).show();
        },

        submit: function () {
            var formValidity = this.isFormValid();
            if (formValidity) {
                var data = $('form').serialize();
                var referer = $("#submit").data("referer");
                var tip = '';
                if ($('#role').val() === 'student') {
                    tip = '正在智能识别验证码';
                } else {
                    tip = '正在登陆';
                }
                var $loading = AMUI.dialog.loading({title: tip});
                $.ajax({
                    type: 'POST',
                    url: '/doLogin',
                    data: data,
                    success: function (res) {
                        $loading.modal('close');
                        if (!res.result) {
                            AMUI.dialog.alert({
                                title: '登录失败',
                                content: '请检查你的输入~'
                            });
                        } else {
                            location.href = referer;
                        }
                    }
                });
                return false;
            }
            return false;
        }
    });
});