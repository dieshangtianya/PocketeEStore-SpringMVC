function ValidateResult(errors) {
    this.result = true;
    this.errors = [];
    if (errors && errors.length > 0) {
        this.result = false;
        this.errors = errors;
    }
    this.getErrorMessageHtml = function () {
        var errorHtml = $('<div></div>');
        for (var index = 0; index < this.errors.length; index++) {
            errorHtml.append($('<p>' + this.errors[index] + '</p>'));
        }
        return errorHtml.html();
    }
}
