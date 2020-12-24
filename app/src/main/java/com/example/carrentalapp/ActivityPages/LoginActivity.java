package com.example.carrentalapp.ActivityPages;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.room.Room;

import com.example.carrentalapp.Database.CustomerDao;
import com.example.carrentalapp.Database.InsuranceDao;
import com.example.carrentalapp.Database.Project_Database;
import com.example.carrentalapp.Database.VehicleCategoryDao;
import com.example.carrentalapp.Database.VehicleDao;
import com.example.carrentalapp.Model.Customer;
import com.example.carrentalapp.Model.Insurance;
import com.example.carrentalapp.Model.Vehicle;
import com.example.carrentalapp.Model.VehicleCategory;
import com.example.carrentalapp.R;
import com.example.carrentalapp.Session.Session;


public class LoginActivity extends AppCompatActivity {

    private TextView register;
    private TextView forgotPass;
    private Button login;

    private EditText email;
    private EditText password;

    private Project_Database db;

    private Button customer;
    private Button vehicleCategory;
    private Button vehicle;

    private Button populate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //IF USER ALREADY LOGGED IN => REDIRECT TO HOME PAGE
        Boolean isLoggedIn = Boolean.valueOf(Session.read(LoginActivity.this,"isLoggedIn","false"));
        if(isLoggedIn){
            Intent homePage = new Intent(LoginActivity.this,UserViewActivity.class);
            startActivity(homePage);
        }

        initComponents();
        clickListenHandler();

    }

    //This will initialize all the clickable components in Login page
    private void initComponents(){

        //Register Button
        register = findViewById(R.id.register);

        //Login Button
        login = findViewById(R.id.login);

        //Forgot Password Button
        forgotPass = findViewById(R.id.forgot_password);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        customer = findViewById(R.id.customer);
        vehicleCategory = findViewById(R.id.vehicleCategory);
        vehicle = findViewById(R.id.vehicle);

        populate = findViewById(R.id.populate);

        db = Room.databaseBuilder(getApplicationContext(), Project_Database.class, "car_rental_db").allowMainThreadQueries().build();
    }



    //This will handle all the click events on the login page
    private void clickListenHandler(){

        //Register Listener
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerPage = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(registerPage);
            }
        });

        //Login Listener
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerDao customerDao = db.customerDao();
                Customer check = customerDao.findUser(email.getText().toString(),password.getText().toString());

                if(check != null){
                    Session.save(LoginActivity.this,"customerID",check.getCustomerID()+"");
                    Session.save(LoginActivity.this,"isLoggedIn","true");

                    Intent homePage = new Intent(LoginActivity.this,UserViewActivity.class);
                    homePage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(homePage);
                }else{
                    toast("Unsuccessful");
                }
            }
        });

        //Forgot Password Listener
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.vehicleCategoryDao().updateQuantity("Тракторы");
                db.vehicleCategoryDao().updateQuantity("Комбаины");
                db.vehicleCategoryDao().updateQuantity("Культиваторы");
                toast("Updated All");
            }
        });

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerDao customerDao = db.customerDao();
                for(Customer c: customerDao.getAll()){
                    Log.d("MainActivity", "CUSTOMER => " + c.toString());
                }
            }
        });

        vehicleCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VehicleCategoryDao vehicleCategoryDao = db.vehicleCategoryDao();
                for(VehicleCategory c: vehicleCategoryDao.getAllCategory()){
                    Log.d("MainActivity", "VEHICLE CATEGORY => " + c.toString());
                }
            }
        });

        vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VehicleDao vehicleDao = db.vehicleDao();
                for(Vehicle c: vehicleDao.getAll()){
                    Log.d("MainActivity", "VEHICLE => " + c.toString());
                }
            }
        });


        populate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VehicleCategoryDao vehicleCategoryDao = db.vehicleCategoryDao();
                VehicleDao vehicleDao = db.vehicleDao();
                InsuranceDao insuranceDao = db.insuranceDao();

                VehicleCategory vc1 = new VehicleCategory("Тракторы",100,-47032,"https://www.pngkey.com/png/full/78-781169_tractor-png.png");
                VehicleCategory vc2 = new VehicleCategory("Комбаины",101,-13936668,"https://webstockreview.net/images/combine-png-images.png");
                VehicleCategory vc3 = new VehicleCategory("Культиваторы",102,-4068,"https://hagri.ru/upload/iblock/974/9744a22754f37fbf2106ee5dcdf71c3f.png");

                vehicleCategoryDao.insert(vc1);
                vehicleCategoryDao.insert(vc2);
                vehicleCategoryDao.insert(vc3);



                Vehicle v1 = new Vehicle(273,65.5,5,6497,"nissan","altima",2020,"Тракторы",true,"https://65e81151f52e248c552b-fe74cd567ea2f1228f846834bd67571e.ssl.cf1.rackcdn.com/ldm-images/2020-Nissan-Altima-Color-Super-Black.png");
                Vehicle v2 = new Vehicle(285,54.8,5,4578,"toyota","avalon",2020,"Тракторы",true,"https://img.sm360.ca/ir/w640h390c/images/newcar/ca/2020/toyota/avalon/limited/sedan/main/2020_toyota_avalon_LTD_Main.png");
                Vehicle v3 = new Vehicle(287,50.99,5,1379,"subaru","wrx",2020,"Тракторы",true,"https://img.sm360.ca/ir/w640h390c/images/newcar/ca/2020/subaru/wrx/base-wrx/sedan/exteriorColors/12750_cc0640_001_d4s.png");

                Vehicle v4 = new Vehicle(265,58.89,5,6490,"kia","telluride",2020,"Комбаины",true,"https://www.cstatic-images.com/car-pictures/xl/usd00kis061c021003.png");
                Vehicle v5 = new Vehicle(229,86.5,5,4970,"lincoln","aviator",2020,"Комбаины",true,"https://www.cstatic-images.com/car-pictures/xl/usd00lis021b021003.png");
                Vehicle v6 = new Vehicle(219,95.0,5,595,"ford","explorer",2020,"Комбаины",true,"https://www.cstatic-images.com/car-pictures/xl/usd00fos102d021003.png");

                Vehicle v7 = new Vehicle(297,56.0,2,200,"chevrolet","camaro",2020,"Культиваторы",false,"https://www.cstatic-images.com/car-pictures/xl/usc90chc022b021003.png");

                vehicleDao.insert(v1);
                vehicleDao.insert(v2);
                vehicleDao.insert(v3);
                vehicleDao.insert(v4);
                vehicleDao.insert(v5);
                vehicleDao.insert(v6);
                vehicleDao.insert(v7);

                Insurance i1 = new Insurance("None",0);
                Insurance i2 = new Insurance("Basic",15);
                Insurance i3 = new Insurance("Premium",25);
                insuranceDao.insert(i1);
                insuranceDao.insert(i2);
                insuranceDao.insert(i3);

            }
        });
    }

    //DEBUGING
    private void toast(String txt){
        Toast toast = Toast.makeText(getApplicationContext(),txt,Toast.LENGTH_SHORT);
        toast.show();
    }
}
