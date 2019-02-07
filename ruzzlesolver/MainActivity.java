package com.HRDS.ruzzlesolver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	List wordsList=new ArrayList();
	int m=0,n = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getTitle().equals("Initiate"))
		{
			initiate();
		}
		else
		{
		TextView v=new TextView(getApplicationContext());
		v.setText("Created by:- \n   Sourabh Dalodia");
		v.setTextColor(Color.RED);
		v.setTextSize(22f);
		Dialog d=new Dialog(MainActivity.this);
	    d.setContentView(v);
	    d.setTitle("Ruzzle Solver");
	    d.show();
		}
		return true;
	}
	
	public void solve(View v){
	    
		EditText et=(EditText) findViewById(R.id.editText1);
	    String ip=et.getText().toString();
	    
	    int i=0;
	    
	    String tmp="";
	    int count=0;
	    
	    try{
	    for(;count<2;i++)
	    {
	    	if(ip.charAt(i)==' ' && count==0)
	    	{
	    		m=Integer.parseInt(tmp);
	    		tmp="";
	    		count++;
	    		continue;
	    	}
	    	else if(ip.charAt(i)==' ' && count==1)
	    	{
	    		n=Integer.parseInt(tmp);
	    		tmp="";
	    		count++;
	    		continue;
	    	}
	    	tmp=""+ip.charAt(i);
	    }
	    
	    }catch(Exception e){
	    	Toast.makeText(getApplicationContext(), "Invalid array size", Toast.LENGTH_LONG).show();
	    	return;
	    }
	    
	    
	    char arr1[][]=new char[m][n];
	    //char arr2[][]=new char[m][n];
	    
	    try{
	    	int pos=0;
	    for(;i<ip.length();i+=2,pos++)
	    {
	    	arr1[pos/n][pos%n]=ip.charAt(i);
	    }
	    if(pos!=(m*n))
	    	pos=m/0;
	    
	    }catch(Exception e){
	    	Toast.makeText(getApplicationContext(), "Invalid array items", Toast.LENGTH_LONG).show();
	    	return;
	    }
	    
	    int p=0;
	    for(int i1=0;i1<m;i1++)
	    {
	    	for(int i2=0;i2<n;i2++)
		    {	    		
		        find("", i1, i2, arr1);
		    	p++;
		    }
	    }
	    
	    ListView lv=(ListView) findViewById(R.id.listView1);
	    ArrayAdapter<String> adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,wordsList);  
	    lv.setAdapter(adapter);
	    Toast.makeText(getApplicationContext(), wordsList.size()+" words found.", Toast.LENGTH_LONG).show();
	}
	
	public void find(String word,int x,int y,char a[][]){
		char b[][]=new char[m][n];
		for(int i11=0;i11<m;i11++)
	    {
	    	for(int i12=0;i12<n;i12++)
		    {
	    		b[i11][i12]=a[i11][i12];
		    }
	    }
		
		word+=b[x][y];
		
		if(word.length()>=2)
			if(dictionary(word))
				wordsList.add(word);
		
		b[x][y]=0;
		
		if((x-1>=0 && y-1>=0) && b[x-1][y-1]!=0)
		{
				find(word,x-1,y-1,b);
		}
		
	    if((x-1>=0) && b[x-1][y]!=0)
		{
				find(word,x-1,y,b);
		}
	    
	    if((x-1>=0 && y+1<n) && b[x-1][y+1]!=0)
		{
				find(word,x-1,y+1,b);
		}
	    
	    if((y-1>=0) && b[x][y-1]!=0)
		{
				find(word,x,y-1,b);
		}
	    
	    if((y+1<n) && b[x][y+1]!=0)
		{
				find(word,x,y+1,b);
		}
	    
	    if((x+1<m && y-1>=0) && b[x+1][y-1]!=0)
		{
				find(word,x+1,y-1,b);
		}
		
	    if((x+1<m) && b[x+1][y]!=0)
		{
				find(word,x+1,y,b);
		}
	    
	    if((x+1<m && y+1<n) && b[x+1][y+1]!=0)
		{
				find(word,x+1,y+1,b);
		}	
	}
	
	public boolean dictionary(String word){
		
		String path="";
		for(int i=0;i<word.length();i++)
		{
			path+="/"+word.charAt(i);
		}
		
		File f=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Sherry/dict"+path+"/yes.txt");
		if(f.isFile() && f.exists())
			return true;
		
		return false;
	}
	
public String readFile(File f){
	
	if(f.isFile())
	{
	String text="";
	try {
		FileReader fr=new FileReader(f.getAbsolutePath());
		BufferedReader bf=new BufferedReader(fr);
		String line=bf.readLine();
	   
		while(line!=null)
		{
			text+=line;
			text+='\n';
			line =bf.readLine();
		}
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

return text;
	}
	else
		return "Not a file";
}


public void initiate(){
	File x=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Sherry/dict");
	x.mkdir();
	File f=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Sherry/words.txt");
	String text=readFile(f);
	String word="";
	
	for(int i=0;i<text.length();i++)
	{
		String pword=word;
		word+="/"+text.charAt(i);
		File folder,file;
		
		if(text.charAt(i)=='\n')
		{
			file=new File(x.getAbsolutePath()+pword+"/yes.txt");
			if(!(file.exists()))
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			word="";
		}
		else
		{
			folder=new File(x.getAbsolutePath()+word);
			if(!(folder.exists()))
				folder.mkdir();
		}
	}
}

	
}
