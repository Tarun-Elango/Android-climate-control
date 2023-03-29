package com.example.a390cc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

//this class for the dialog box for the cop rating, inside analytics -> store analytics

public class dialog extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("About COP")
                .setMessage("The coefficient of performance (COP) is a performance rating that indicates how efficient a heat pump or air conditioner is at transferring heat compared to the amount of electricity it consumes.. \n" +
                        "\n A higher score indicates that the unit would perform more efficiently..")
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }
}
