package com.joker.demo;

import com.jcraft.jsch.JSchException;
import com.joker.util.SSHUtil;

import java.io.IOException;

public class SSHDemo {

    public static void main(String[] args) {
        try {
            SSHUtil.runCommand("47.105.168.197", "root", "Lxq931129", "java -version");
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
