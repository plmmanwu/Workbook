package com.wlwoon.base.common;

import android.Manifest;

/**
 * Created by wxy on 2020/7/10.
 */

public class PermissonsHelp {

    public static String toPermission(Permissions permissions) {
        if (permissions == Permissions.CALENDAR) {
            return Manifest.permission_group.CALENDAR;
        } else if (permissions == Permissions.CAMERA) {
            return Manifest.permission_group.CAMERA;
        }else if (permissions == Permissions.CONTACTS) {
            return Manifest.permission_group.CONTACTS;
        }else if (permissions == Permissions.LOCATION) {
            return Manifest.permission_group.LOCATION;
        }else if (permissions == Permissions.MICROPHONE) {
            return Manifest.permission_group.MICROPHONE;
        }else if (permissions == Permissions.PHONE) {
            return Manifest.permission_group.PHONE;
        }else if (permissions == Permissions.SENSORS) {
            return Manifest.permission_group.SENSORS;
        }else if (permissions == Permissions.SMS) {
            return Manifest.permission_group.SMS;
        }else if (permissions == Permissions.STORAGE) {
            return Manifest.permission_group.STORAGE;
        }

        return null;
    }

    public static String[] toPermissionArray(Permissions permissions) {
        if (permissions == Permissions.CALENDAR) {
            return new String[]{Manifest.permission.READ_CALENDAR,Manifest.permission.WRITE_CALENDAR};
        } else if (permissions == Permissions.CAMERA) {
            return new String[]{Manifest.permission.CAMERA};
        }else if (permissions == Permissions.CONTACTS) {
            return new String[]{Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS};
        }else if (permissions == Permissions.LOCATION) {
            return new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
        }else if (permissions == Permissions.MICROPHONE) {
            return new String[]{Manifest.permission.RECORD_AUDIO};
        }else if (permissions == Permissions.PHONE) {
            return new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.CALL_PHONE,Manifest.permission.READ_CALL_LOG,Manifest.permission.WRITE_CALL_LOG,Manifest.permission.ADD_VOICEMAIL,Manifest.permission.USE_SIP,Manifest.permission.PROCESS_OUTGOING_CALLS};
        }else if (permissions == Permissions.SENSORS) {
            return new String[]{Manifest.permission.BODY_SENSORS};
        }else if (permissions == Permissions.SMS) {
            return new String[]{Manifest.permission.SEND_SMS,Manifest.permission.RECEIVE_SMS,Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_WAP_PUSH,Manifest.permission.RECEIVE_MMS,};
        }else if (permissions == Permissions.STORAGE) {
            return  new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
        }

        return null;
    }
}
