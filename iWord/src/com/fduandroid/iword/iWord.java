package com.fduandroid.iword;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

public class iWord extends Activity {
	
	static final int DIALOG_SELECT_LIST = 0;
    static final int PROGRESS_DIALOG = 1;

    public static final String PREFS_NAME = "PrefsFile";
    public static final String LIST_NAME="ListName";
    public static final String LIST_FULL_NAME="ListFullName";
    public static final String IS_RANDOM="IsRandom";

    private ImageButton selectListButton;
    private ImageButton startRecite;
    private ImageButton meaningTest;
    private ImageButton spellTest;
    private TextView listName;
    
    private ProgressDialog progressDialog;
    static public ArrayList<Word> wordList;
    static public String nowListName;
    static public String myListName;
    
    public void setPref(String PrefName,String key,String value) {
	    SharedPreferences settings=getSharedPreferences(PrefName,0);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putString(key, value);
	    editor.commit();
    }
    
    public String getPref(String PrefName,String key,String defValue) {
 	    SharedPreferences settings=getSharedPreferences(PrefName,0);
 	    String ln=settings.getString(key, defValue);
 	    return ln;
    }
    
    private void updateListName(String listFullName,String listname) {
        setPref(PREFS_NAME,LIST_FULL_NAME,listFullName);
        setPref(PREFS_NAME,LIST_NAME,listname);
	    listName.setText(listFullName);
	    myListName=listname;
    }
    
    protected Dialog onCreateDialog(int id) {
    	 Dialog dialog;
        switch(id) {
        case DIALOG_SELECT_LIST:
        	 final CharSequence[] items =  getResources().getStringArray(R.array.VListFullName);
        	 AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	 builder.setCancelable(true)
        	           .setTitle(getResources().getString(R.string.PleaseSelectAList))
        	           .setNegativeButton("Cancel",  new DialogInterface.OnClickListener() {
        	                 public void onClick(DialogInterface dialog, int id) {
        	                       dialog.cancel();
        	                 }
        	            })
        	           .setItems(items, new DialogInterface.OnClickListener() {
        	                 public void onClick(DialogInterface dialog, int item) {
        	                       updateListName(items[item].toString(),getResources().getStringArray(R.array.VListName)[item].toString());
        	                 }
        	           });
        	 dialog = (AlertDialog) builder.create();
            break;
        case PROGRESS_DIALOG:
            progressDialog = new ProgressDialog(iWord.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(getResources().getString(R.string.Loading));
            progressDialog.setCancelable(false);
            return progressDialog;
        default:
            dialog = null;
        }
        return dialog;
    }
    
    private void initLayout() {
   	    listName=(TextView)findViewById(R.id.ListName);
        listName.setText(getPref(PREFS_NAME,LIST_FULL_NAME,"大学英语四级"));
        
        selectListButton = (ImageButton) findViewById(R.id.SelectListButton);
        selectListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_SELECT_LIST);
            }
        });
        
        startRecite=(ImageButton)findViewById(R.id.StartRecite);
        startRecite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(PROGRESS_DIALOG);
            	
                Thread getListThread=new Thread() {
                	public void run() {
                		getWordList();
                      progressDialog.dismiss();
                 	}                	
                };
                getListThread.start();
                Intent  it=new Intent();
                it.setClass(iWord.this, Vocabulary.class);
                startActivity(it);
                iWord.this.finish();
            }
        });
        
        meaningTest=(ImageButton)findViewById(R.id.MeaningTest);
        meaningTest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(PROGRESS_DIALOG);
            	
                Thread getListThread=new Thread() {
                	public void run() {
                		getWordList();
                      progressDialog.dismiss();
                 	}                	
                };
                getListThread.start();
                Intent  it=new Intent();
                it.setClass(iWord.this, MeaningTest.class);
                startActivity(it);
                iWord.this.finish();
            }
        });
        
        spellTest=(ImageButton)findViewById(R.id.SpellTest);
        spellTest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(PROGRESS_DIALOG);
            	
                Thread getListThread=new Thread() {
                	public void run() {
                		getWordList();
                      progressDialog.dismiss();
                 	}                	
                };
                getListThread.start();
                Intent  it=new Intent();
                it.setClass(iWord.this, SpellTest.class);
                startActivity(it);
                iWord.this.finish();
            }
        });
   }
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        
        initLayout();     
    }
    
	public class Word {
		public String Word;
		public String Meaning; 
		public Word() {
		    Word="";
		    Meaning="";
		}
		public Word(String word,String meaning) {
		    Word=word;
		    Meaning=meaning;
		}
	}
    
	private int getListId() {
		    String ln=getPref(iWord.PREFS_NAME,iWord.LIST_NAME,"cet4");    
		    int listId=getResources().getIdentifier(ln,"raw",getPackageName());
		    System.out.print(listId);
            if (listId==0) return R.raw.cet4;
            else return listId;
	 }
	   
    private void getWordList() {
    	if (nowListName==listName.getText()) return;
        if (wordList==null) wordList=new ArrayList<Word>();
        else wordList.clear();
        nowListName=listName.getText().toString();
        InputStream is = getResources().openRawResource(getListId());
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        String line,word,meaning;
      
        try {
           String[] lines;
            while((line = br.readLine()) != null) {
            	line=line.trim();
            	if (line.length()>0) {
            		 lines=line.split("#");
            		 word=lines[0];
        	        meaning=lines[1];
        	        wordList.add(new Word(word,meaning));
               }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}

}