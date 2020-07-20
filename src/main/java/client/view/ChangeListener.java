package client.view;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class ChangeListener implements javafx.beans.value.ChangeListener<String>{
    private int maxLength;
    private TextField textField;

    public ChangeListener(int maxLength, TextField textField) {
        this.maxLength = maxLength;
        this.textField = textField;
    }

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        if (newValue == null)
            return;
        if (newValue.length() >  maxLength)
            textField.setText(oldValue);
        else
            textField.setText(newValue);
    }
}
