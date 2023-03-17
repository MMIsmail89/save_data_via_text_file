package com.example.savedataviatextfile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.savedataviatextfile.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ArrayList<Person> personList;
    public static final String FILE_NAME_TXT = "person.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //
        personList = new ArrayList<Person>();
        //
        personList = readingDataFromFile(FILE_NAME_TXT);
        String text = arrayListToString(personList);
        binding.mainTvMedium.setText(text);
        //
        binding.mainBtnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingEntryToTheList(v, personList);
                //
                String text = arrayListToString(personList);

                binding.mainTvMedium.setText(text);
            }
        });
        //
        binding.mainBtnSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writingDataToFile(v, FILE_NAME_TXT);
            }
        });
        //

    }
    //



    public void addingEntryToTheList(View v, ArrayList<Person> people){
        String name = binding.mainEtName.getText().toString().trim();
        String surname = binding.mainEtSurname.getText().toString().trim();
        //
        people.add(new Person(name, surname));
        //

    }

    public String arrayListToString(ArrayList<Person> people){
        String result = "";
        for (int i = 0; i < people.size(); ++i){
            result += people.get(i).getName() + ", " +
                    people.get(i).getSurname() + "\n";
        }
        return result;
    }

    public void writingDataToFile(View v, String FILE_NAME){
        try{
            FileOutputStream fileOutputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            for(int i=0; i<personList.size();++i) {
                outputStreamWriter.write(personList.get(i).getName()+", "+
                        personList.get(i).getSurname()+"\n"); }
            outputStreamWriter.flush();
            outputStreamWriter.close();
            Toast.makeText(MainActivity.this, R.string.successfully_saved,
                    Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    //
    public ArrayList<Person> readingDataFromFile(String FILE_NAME){
        ArrayList<Person> theList= new ArrayList<Person>();
        //
        File file = getApplicationContext().getFileStreamPath(FILE_NAME);
        String lineFromFile;
        if(file.exists()) {
            try {
                //
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        openFileInput(FILE_NAME)  ));
                //
                while( (lineFromFile = reader.readLine()) != null) {
                    StringTokenizer tokenizer = new StringTokenizer(lineFromFile, ", ");
                    theList.add(new Person(tokenizer.nextToken(), tokenizer.nextToken()));
                }
                //
                reader.close();
                //
                Toast.makeText(MainActivity.this, getString(R.string.reading_done),
                        Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(MainActivity.this, getString(R.string.file_not_found),
                    Toast.LENGTH_SHORT).show();
        }
        return theList;
    }
}