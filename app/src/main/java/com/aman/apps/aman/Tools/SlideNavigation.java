package com.aman.apps.aman.Tools;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.TextView;

import com.alexzh.circleimageview.CircleImageView;
import com.aman.apps.aman.Dialogs.About_Dialog;
import com.aman.apps.aman.Fragments.CartFragment;
import com.aman.apps.aman.Fragments.CategoriesFragment;
import com.aman.apps.aman.Fragments.ExploreFragment;
import com.aman.apps.aman.Fragments.Favourites_Fragment;
import com.aman.apps.aman.Fragments.HomeFragment;
import com.aman.apps.aman.Fragments.LoginFragment;
import com.aman.apps.aman.Fragments.LogoutFragment;
import com.aman.apps.aman.Fragments.ProfileFragment;
import com.aman.apps.aman.R;



public class SlideNavigation {
    int fragmnetholder;
    TextView user_name, home, hot, cart, category, about, logout, history;
    CircleImageView user_img;


    public SlideNavigation(int fragmnetholder) {
        this.fragmnetholder = fragmnetholder;
    }


    public void initSlideMenu(final Activity activity, final FragmentManager fragmentManager, final DrawerLayout drawerLayout) {

        user_img = activity.findViewById(R.id.user_img);
        home = activity.findViewById(R.id.menu_home);
        hot = activity.findViewById(R.id.menu_hot);
        cart = activity.findViewById(R.id.menu_cart);
        category = activity.findViewById(R.id.menu_cat);
        about = activity.findViewById(R.id.menu_about);
        history = activity.findViewById(R.id.menu_his);
        logout = activity.findViewById(R.id.menu_logout);
        user_name = activity.findViewById(R.id.menu_username);


        user_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                fragmentManager
                        .beginTransaction().setCustomAnimations(R.anim.animate_slide_left_enter, R.anim.animate_slide_left_exit)
                        .add(R.id.main_fragment_container, new ProfileFragment(), "ProfileFragment")
                        .addToBackStack("ProfileFragment")
                        .commit();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                fragmentManager
                        .beginTransaction().setCustomAnimations(R.anim.animate_slide_left_enter, R.anim.animate_slide_left_exit)
                        .replace(fragmnetholder, new HomeFragment(), "HomeFragment")
                        .commit();
            }
        });

        hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                fragmentManager
                        .beginTransaction().setCustomAnimations(R.anim.animate_slide_left_enter, R.anim.animate_slide_left_exit)
                        .replace(fragmnetholder, new ExploreFragment(), "ExploreFragment")
                        .addToBackStack("ExploreFragment")
                        .commit();

            }
        });

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                fragmentManager
                        .beginTransaction().setCustomAnimations(R.anim.animate_slide_left_enter, R.anim.animate_slide_left_exit)
                        .replace(fragmnetholder, new CategoriesFragment(), "CategoriesFragment")
                        .addToBackStack("CategoriesFragment")
                        .commit();

            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                fragmentManager
                        .beginTransaction().setCustomAnimations(R.anim.animate_slide_left_enter, R.anim.animate_slide_left_exit)
                        .replace(fragmnetholder, new CartFragment(), "CartFragment")
                        .addToBackStack("CartFragment")
                        .commit();
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                fragmentManager
                        .beginTransaction().setCustomAnimations(R.anim.animate_slide_left_enter, R.anim.animate_slide_left_exit)
                        .replace(fragmnetholder, new Favourites_Fragment(), "FavouritesFragment")
                        .addToBackStack("FavouritesFragment")
                        .commit();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(GravityCompat.START);
                About_Dialog about_dialog = new About_Dialog();
                about_dialog.show(fragmentManager, about_dialog.getTag());

            }
        });

        user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                fragmentManager
                        .beginTransaction().setCustomAnimations(R.anim.animate_slide_left_enter, R.anim.animate_slide_left_exit)
                        .replace(fragmnetholder, new LoginFragment(), "LoginFragment")
                        .addToBackStack("LoginFragment")
                        .commit();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                fragmentManager
                        .beginTransaction().setCustomAnimations(R.anim.animate_slide_left_enter, R.anim.animate_slide_left_exit)
                        .replace(fragmnetholder, new LogoutFragment(), "LogoutFragment")
                        .commit();
            }
        });
    }


}
