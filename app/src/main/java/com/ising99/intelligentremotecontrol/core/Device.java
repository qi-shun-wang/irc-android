package com.ising99.intelligentremotecontrol.core;


/**
 * Created by shun on 2018/3/28.
 *
 */

public class Device {

    private String Address;
    private String BackupAddress;
    private String Name;
    private String Settings;

    public Device(String address, String backupAddress, String name, String settings) {
        Address = address;
        BackupAddress = backupAddress;
        Name = name;
        Settings = settings;
    }

    public String getAddress() {
        return Address;
    }

    public String getName() {
        return Name;
    }

    public String getSettings() {
        return Settings;
    }

    public void setBackupAddress(String backupAddress) {
        BackupAddress = backupAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Device device = (Device) o;

        return (Address != null ? Address.equals(device.Address) : device.Address == null) && (BackupAddress != null ? BackupAddress.equals(device.BackupAddress) : device.BackupAddress == null) && (Name != null ? Name.equals(device.Name) : device.Name == null) && (Settings != null ? Settings.equals(device.Settings) : device.Settings == null);
    }

    @Override
    public int hashCode() {
        int result = Address != null ? Address.hashCode() : 0;
        result = 31 * result + (BackupAddress != null ? BackupAddress.hashCode() : 0);
        result = 31 * result + (Name != null ? Name.hashCode() : 0);
        result = 31 * result + (Settings != null ? Settings.hashCode() : 0);
        return result;
    }

    public String getBackupAddress() {
        return BackupAddress;
    }
}
