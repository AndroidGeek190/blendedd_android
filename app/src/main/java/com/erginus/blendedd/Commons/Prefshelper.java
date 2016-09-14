package com.erginus.blendedd.Commons;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * Created by paramjeet on 29/9/15.
 */
public class Prefshelper {
    private Context context;
    public static SharedPreferences preferences;

    public Prefshelper(Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public Context getContext() {
        return context;
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public void storeUserFirstNameToPreference(String name) {
        Editor edit = getPreferences().edit();
        edit.putString("user_first_name", name);
        edit.commit();

    }
    public void storeUserlastNameToPreference(String name) {
        Editor edit = getPreferences().edit();
        edit.putString("user_last_name", name);
        edit.commit();

    }
    public void storeEmailToPreference(String email) {
    Editor edit = getPreferences().edit();
        edit.putString("user_email", email);
        edit.commit();

    }
    public void storeFacebookIdToPreference(String email) {
        Editor edit = getPreferences().edit();
        edit.putString("user_facebook_id", email);
        edit.commit();

    }
    public void storeBirthdayToPreference(String bday) {
        Editor edit = getPreferences().edit();
        edit.putString("user_dob", bday);
        edit.commit();

    }
    public void storeUserIdToPreference(String userid) {
        Editor edit = getPreferences().edit();
        edit.putString("user_id", userid);
        edit.commit();

    }
    public void storeUserLoginToPreference(String user) {
        Editor edit = getPreferences().edit();
        edit.putString("user_login", user);
        edit.commit();

    }
    public void storePasswordToPreference(String pwd) {
        Editor edit = getPreferences().edit();
        edit.putString("user_login_password", pwd);
        edit.commit();

    }
    public void storeConfirmPasswordToPreference(String user) {
        Editor edit = getPreferences().edit();
        edit.putString("confirm_user_login_password", user);
        edit.commit();

    }

    public void storeSecHashToPreference(String sec) {
        Editor edit = getPreferences().edit();
        edit.putString("user_security_hash", sec);
        edit.commit();

    }
    public void storePrimaryContactToPreference(String contact) {
        Editor edit = getPreferences().edit();
        edit.putString("user_primary_contact", contact);
        edit.commit();

    }
    public void storeImageToPreference(String image) {
        Editor edit = getPreferences().edit();
        edit.putString("user_profile_image_url", image);
        edit.commit();

    }
    public void storeAddressLineOneToPreference(String a) {
        Editor edit = getPreferences().edit();
        edit.putString("user_address_line_1", a);
        edit.commit();

    }
    public void storeAddressLineTwoToPreference(String a) {
        Editor edit = getPreferences().edit();
        edit.putString("user_address_line_2", a);
        edit.commit();

    }




    public void storeZipcodeToPreference(String a) {
        Editor edit = getPreferences().edit();
        edit.putString("user_zipcode", a);
        edit.commit();

    }
    public void storeCreditCardNumber(String a) {
        Editor edit = getPreferences().edit();
        edit.putString("user_credit_card_number", a);
        edit.commit();

    }
    public void storeCreditCardName(String a) {
        Editor edit = getPreferences().edit();
        edit.putString("user_credit_card_name", a);
        edit.commit();

    }
    public void storeCreditCardExpiryMonth(String a) {
        Editor edit = getPreferences().edit();
        edit.putString("user_credit_card_expiry_month", a);
        edit.commit();

    }
    public void storeCreditCardExpiryYear(String a) {
        Editor edit = getPreferences().edit();
        edit.putString("user_credit_card_expiry_year", a);
        edit.commit();

    }
    public void storeCreditCardCvv(String a) {
        Editor edit = getPreferences().edit();
        edit.putString("user_credit_card_cvv", a);
        edit.commit();

    }

    public void storeLoginWithToPreference(String login) {
        Editor edit = getPreferences().edit();
        edit.putString("login_with", login);
        edit.commit();

    }
    public void storeCountryIdToPreference(String countryId) {
        Editor edit = getPreferences().edit();
        edit.putString("countries_id", countryId);
        edit.commit();

    }
    public void storeStateIdToPreference(String id) {
        Editor edit = getPreferences().edit();
        edit.putString("states_id", id);
        edit.commit();

    }

    public void storeCityIdToPreference(String id) {
        Editor edit = getPreferences().edit();
        edit.putString("cities_id", id);
        edit.commit();

    }
    public void storePostIdToPreference(String id) {
        Editor edit = getPreferences().edit();
        edit.putString("post_id", id);
        edit.commit();

    }
    public String getPostId() {

        return getPreferences().getString("post_id", "");
    }
    public String getCreditCard() {

        return getPreferences().getString("user_credit_card_number", "");
    }
    public String getFacebookId() {

        return getPreferences().getString("user_facebook_id", "");
    }

    public String getBirthdayFromPreference() {

        return getPreferences().getString("user_dob", "");
    }

    public String getCountryIdFromPreference() {

        return getPreferences().getString("countries_id", "");
    }
    public String getLoginWithFromPreference() {

        return getPreferences().getString("login_with", "");
    }
    public String getUserIdFromPreference() {

        return getPreferences().getString("user_id", "");
    }

    public String getUserSecHashFromPreference() {
        return getPreferences().getString("user_security_hash", "");
    }

    public String getUserFNameFromPreference() {
        return getPreferences().getString("user_first_name", "");
    }
    public String getUserLNameFromPreference() {
        return getPreferences().getString("user_last_name", "");
    }
    public String getUserEmailFromPreference() {
        return getPreferences().getString("user_email", "");
    }
    public String getUserContactFromPreference() {
        return getPreferences().getString("user_primary_contact", "");
    }
    public String getImageFromPreference() {
        return getPreferences().getString("user_profile_image_url", "");
    }
    public String getAddressOne() {
        return getPreferences().getString("user_address_line_1", "");
    }
    public String getAddressTwo() {
        return getPreferences().getString("user_address_line_2", "");
    }
    public String getZipcode() {
        return getPreferences().getString("user_zipcode", "");
    }

    public String getUserLogin() {
        return getPreferences().getString("user_login", "");
    }
    public String getPassword() {
        return getPreferences().getString("user_login_password", "");
    }
    public String getCPassword() {
        return getPreferences().getString("confirm_user_login_password", "");
    }

    public String getStateId() {
        return getPreferences().getString("states_id", "");
    }
    public String getCityId() {
        return getPreferences().getString("cities_id", "");
    }

    public String getCreditCardName() {
        return getPreferences().getString("user_credit_card_name", "");
    }
    public String getExpiryMonth() {
        return getPreferences().getString("user_credit_card_expiry_month", "");
    }
    public String getExpiryYear() {
        return getPreferences().getString("user_credit_card_expiry_year", "");
    }
    public String getCVV() {
        return getPreferences().getString("user_credit_card_cvv", "");
    }
}
