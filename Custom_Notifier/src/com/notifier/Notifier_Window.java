package com.notifier;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.*;
import javax.sound.sampled.*;

import javax.swing.JOptionPane;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;

import javax.swing.SwingConstants;

import java.awt.AWTException;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.JCheckBox;

import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 
import java.util.Scanner;
import java.io.IOException;

public class Notifier_Window {

    private JFrame frame;
    private JTextField TitleBox;
    private JTextField TimeToWaitBox;
    private JTextField ContentsBox;

    /**
     * Variables
     */

    double TimeToWaitVal = 0;
    String MessageTitle = "";
    String MessageContents = "";
    static boolean IsActive = false;
    static boolean Repeat = false;
    static boolean Toggle = false;
    File selectedFile;
    private Clip clip;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Notifier_Window window = new Notifier_Window();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Notifier_Window() {
        JOptionPane.showMessageDialog(null, "Welcome To Custom Notifications!\n            Made By Jason.", "Welcome",
                2);
        initialize();
        frame.setResizable(false);
        System.out.println("App Opened And Is Running...");
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel Title = new JLabel("Custom Notifications", SwingConstants.CENTER);
        Title.setHorizontalAlignment(SwingConstants.CENTER);
        Title.setFont(new Font("Tahoma", Font.BOLD, 24));
        Title.setBounds(155, 11, 297, 29);
        frame.getContentPane().add(Title);

        JLabel ActiveOrNotLBL = new JLabel("NOT ACTIVE");
        ActiveOrNotLBL.setForeground(new Color(255, 0, 0));
        ActiveOrNotLBL.setHorizontalAlignment(SwingConstants.CENTER);
        ActiveOrNotLBL.setBounds(228, 51, 135, 14);
        frame.getContentPane().add(ActiveOrNotLBL);

        TitleBox = new JTextField();
        TitleBox.setFont(new Font("Tahoma", Font.BOLD, 16));
        TitleBox.setText("Enter Notification Title...");
        TitleBox.setHorizontalAlignment(SwingConstants.CENTER);
        TitleBox.setBounds(169, 76, 250, 50);
        frame.getContentPane().add(TitleBox);
        TitleBox.setColumns(10);

        TimeToWaitBox = new JTextField();
        TimeToWaitBox.setText("Minutes To Wait...");
        TimeToWaitBox.setHorizontalAlignment(SwingConstants.CENTER);
        TimeToWaitBox.setFont(new Font("Tahoma", Font.BOLD, 16));
        TimeToWaitBox.setColumns(10);
        TimeToWaitBox.setBounds(169, 183, 250, 50);
        frame.getContentPane().add(TimeToWaitBox);

        JLabel BottomText = new JLabel(
                "Only Enter Text In Notification TextBox And Only Numbers in Minutes For The Timer Delay!");
        BottomText.setFont(new Font("Tahoma", Font.BOLD, 11));
        BottomText.setHorizontalAlignment(SwingConstants.CENTER);
        BottomText.setBounds(10, 321, 564, 29);
        frame.getContentPane().add(BottomText);
        
        JLabel TimerLabel = new JLabel("Time Left For Timer: 0 Seconds/Mins");
        TimerLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        TimerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        TimerLabel.setBounds(169, 262, 250, 14);
        frame.getContentPane().add(TimerLabel);
        
        JPanel OptionsPanel = new JPanel();
        OptionsPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        OptionsPanel.setBounds(10, 76, 135, 183);
        frame.getContentPane().add(OptionsPanel);

        JLabel PanelTitle = new JLabel("Options");
        OptionsPanel.add(PanelTitle);
        PanelTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        PanelTitle.setHorizontalAlignment(SwingConstants.CENTER);

        JCheckBox RepeatCheckBox = new JCheckBox("Repeat Notification");
        RepeatCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Repeat = RepeatCheckBox.isSelected();
                System.out.println("Repeat Notification = " + Repeat);
                ;
            }
        });
        OptionsPanel.add(RepeatCheckBox);
        
        JButton OpenRingtoneChanger = new JButton("Sound Changer");
        OpenRingtoneChanger.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	    // Code For SoundFrame
        	    System.out.println("Opening Change Sound Frame...");
        	    JFrame ChangeSoundFrame = new JFrame("Sound Changer");
        	    ChangeSoundFrame.setVisible(true);
        	    ChangeSoundFrame.setLayout(null); // Switch to null layout
        	    ChangeSoundFrame.setBounds(325, 325, 325, 325);
        	    ChangeSoundFrame.setResizable(false);

        	    // Code For Inside Frame
        	    JLabel SoundChangerTitle = new JLabel("Change Timer Sound");
        	    SoundChangerTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        	    SoundChangerTitle.setHorizontalAlignment(SwingConstants.CENTER);
        	    SoundChangerTitle.setBounds(10, 10, 300, 20); // Set bounds using absolute positioning
        	    ChangeSoundFrame.getContentPane().add(SoundChangerTitle);

        	    JButton SelectFileBTN = new JButton("Select File");
        	    SelectFileBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
        	    SelectFileBTN.setBackground(new Color(255, 255, 255));
        	    SelectFileBTN.setBounds(100, 50, 125, 30);
        	    ChangeSoundFrame.getContentPane().add(SelectFileBTN);

        	    JLabel CurrentFileLBL = new JLabel("Current File: None Selected!");
        	    CurrentFileLBL.setFont(new Font("Tahoma", Font.BOLD, 12));
        	    CurrentFileLBL.setBackground(new Color(255,255,255));
        	    CurrentFileLBL.setBounds(10, 90, 300, 20);
        	    ChangeSoundFrame.getContentPane().add(CurrentFileLBL);
        	    
        	    if (selectedFile != null) {
        	    	CurrentFileLBL.setText("Current File: " + selectedFile.getName());
        	    } else {
        	    	CurrentFileLBL.setText("Current File: None Selected!");
        	    }
        	    
        	    JLabel Disclaimer1 = new JLabel("WAV Files Only!");
        	    Disclaimer1.setFont(new Font("Tahoma",Font.BOLD,15));
        	    Disclaimer1.setBackground(new Color(255,255,255));
        	    Disclaimer1.setBounds(10,90,300,20);
        	    Disclaimer1.move(90, 225);
        	    ChangeSoundFrame.getContentPane().add(Disclaimer1);
        	    
        	    JLabel Disclaimer2 = new JLabel("Full Audio Clip Will Be Played!");
        	    Disclaimer2.setFont(new Font("Tahoma",Font.BOLD,15));
        	    Disclaimer2.setBackground(new Color(255,255,255));
        	    Disclaimer2.setBounds(10,90,300,20);
        	    Disclaimer2.move(42, 250);
        	    ChangeSoundFrame.getContentPane().add(Disclaimer2);
        	    
        	    SelectFileBTN.addActionListener(new ActionListener() {
        	        @Override
        	        public void actionPerformed(ActionEvent e) {
        	            JFileChooser fileChooser = new JFileChooser();
        	            fileChooser.setFileFilter(new FileNameExtensionFilter("wav files", "wav"));
        	            int returnValue = fileChooser.showOpenDialog(null);
        	            if (returnValue == JFileChooser.APPROVE_OPTION) {
        	                selectedFile = fileChooser.getSelectedFile();
        	                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        	                CurrentFileLBL.setText("Current File: " + selectedFile.getName());
        	            }
        	        }
        	    });
        	}
        });
        OpenRingtoneChanger.setFont(new Font("Tahoma", Font.BOLD, 12));
        OpenRingtoneChanger.setBackground(new Color(255, 255, 255));
        OptionsPanel.add(OpenRingtoneChanger);

        OpenRingtoneChanger.setFont(new Font("Tahoma", Font.BOLD, 12));
        OpenRingtoneChanger.setBackground(new Color(255, 255, 255));
        OptionsPanel.add(OpenRingtoneChanger);

        JButton ActivateBTN = new JButton("Activate!");
        ActivateBTN.setFont(new Font("Tahoma", Font.BOLD, 11));
        ActivateBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Toggle = !Toggle;

                try {
                    TimeToWaitVal = Double.parseDouble(TimeToWaitBox.getText());
                    TimeToWaitVal = TimeToWaitVal * 1000;
                    if (Toggle) {
                        IsActive = true;
                        ActiveOrNotLBL.setText("Active");
                        ActiveOrNotLBL.setForeground(new Color(0, 255, 0));
                        System.out.println("Notifications Are Now Active.");
                        JOptionPane.showMessageDialog(null,
                                "The Notification Will Now Appear In " + TimeToWaitVal / 1000+ " Minutes!", "Success!", 1);
                        ActivateBTN.setText("Deactivate!");
                        ActivateBTN.setFont(new Font("tahoma", Font.BOLD, 8));
                    } else {
                        IsActive = false;
                        ActiveOrNotLBL.setText("Not Active");
                        ActiveOrNotLBL.setForeground(new Color(255, 0, 0));
                        System.out.println("Notifications Not Active.");
                        JOptionPane.showMessageDialog(null,
                                "The Notifications Will No Longer Appear Until Turned Back On!", "Success!", 1);
                        ActivateBTN.setText("Activate!");
                        ActivateBTN.setFont(new Font("tahoma", Font.BOLD, 11));
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "You Did Not Enter A Number!", "Error!", 0);
                }
                final Thread thread = new Thread(new Runnable() {
                    public void run() {
                        
                        SystemTray tray = SystemTray.getSystemTray();
                        Image image = Toolkit.getDefaultToolkit().createImage("Information_Icon.png");
                        TrayIcon trayIcon = new TrayIcon(image, "Notification");
                        
                        try {
                            tray.add(trayIcon);
                        } catch (AWTException e) {
                            e.printStackTrace();
                        }
                        
                        while (IsActive) {
                            try {
                                MessageTitle = TitleBox.getText();
                                MessageContents = ContentsBox.getText();
                                TimerLabel.setText("Timer Ending In " + TimeToWaitVal / 1000 + " Minutes");
                                System.out.println("Time Left " + TimeToWaitVal/1000 + " Minutes!");
                                while(TimeToWaitVal>-1) {
                                	Thread.sleep(60000);
                                	TimeToWaitVal = TimeToWaitVal - 1000;
                                	TimerLabel.setText("Timer Ending In " + TimeToWaitVal / 1000 + " Minutes");
                                    System.out.println("Time Left " + TimeToWaitVal/1000 + " Minutes!");
                                    break;
                                }
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                            
                            if (TimeToWaitVal == 0 && Repeat) {
                                TimeToWaitVal = Double.parseDouble(TimeToWaitBox.getText());
                                TimeToWaitVal = TimeToWaitVal * 1000;
                                System.out.println("Resetting Timer...");
                                
                                trayIcon.displayMessage(MessageTitle, MessageContents, TrayIcon.MessageType.INFO);
                                System.out.println("Notification: " + MessageTitle + " Sent!");
                                
                                // Plays The Audio Clip
                                try {
                                	if (selectedFile != null) {
                                		AudioInputStream audioStream = AudioSystem.getAudioInputStream(selectedFile.getAbsoluteFile());
                                		clip = AudioSystem.getClip();
                                		clip.open(audioStream);
                                		clip.start();
                                	}
								} catch (UnsupportedAudioFileException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								} catch (LineUnavailableException e) {
									e.printStackTrace();
								}
                                
                            } else if(!Repeat) {
                                
                            	// Displays The Notification
                                trayIcon.displayMessage(MessageTitle, MessageContents, TrayIcon.MessageType.INFO);
                                System.out.println("Notification: " + MessageTitle + " Sent!");
                                
                                // Plays The Custom Sound If Needed
                                
                                try {
                                	if (selectedFile != null) {
                                		AudioInputStream audioStream = AudioSystem.getAudioInputStream(selectedFile.getAbsoluteFile());
                                		clip = AudioSystem.getClip();
                                		clip.open(audioStream);
                                		clip.start();
                                	}
								} catch (UnsupportedAudioFileException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								} catch (LineUnavailableException e) {
									e.printStackTrace();
								}
                                
                                // Ends The Timer
                                System.out.println("Timer Has Ended...");
                                System.out.println("Timer Is Ending Because Repeat Is Disabled...");
                                IsActive = false;
                                ActiveOrNotLBL.setText("Not Active");
                                ActiveOrNotLBL.setForeground(new Color(255,0,0));
                                ActivateBTN.setText("Activate!");
                                TimerLabel.setText("Timer Ending In 0 Minutes!");
                                ActivateBTN.setFont(new Font("tahoma", Font.BOLD, 11));
                                Toggle = !Toggle;
                                break;
                            }
                        }
                    }
                });
                thread.start();
            }
        });
        ActivateBTN.setBackground(new Color(128, 255, 0));
        ActivateBTN.setBounds(250, 287, 89, 23);
        frame.getContentPane().add(ActivateBTN);

        ContentsBox = new JTextField();
        ContentsBox.setText("Enter Notification Text Here...");
        ContentsBox.setHorizontalAlignment(SwingConstants.CENTER);
        ContentsBox.setFont(new Font("Tahoma", Font.BOLD, 16));
        ContentsBox.setColumns(10);
        ContentsBox.setBounds(169, 129, 250, 50);
        frame.getContentPane().add(ContentsBox);

        JButton InformationBox1 = new JButton("?");
        InformationBox1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Information Box Pressed...");
                JOptionPane.showMessageDialog(null,
                        "Type The Title Of The Notification In The First TextBox \nType The Notification Contents In The Second TextBox \nAnd Type The Time In Minutes You Want The Notification To Appear In.",
                        "How This Works", 1);
            }
        });
        InformationBox1.setFont(new Font("Tahoma", Font.BOLD, 20));
        InformationBox1.setBounds(429, 88, 50, 23);
        frame.getContentPane().add(InformationBox1);

        JButton InformationBox2 = new JButton("?");
        InformationBox2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Information Box 2 Pressed...");
                JOptionPane.showMessageDialog(null,
                        "This Is The Options Panel \nThis Is Where You Control The Notifications.", "Information", 1);
            }
        });
        InformationBox2.setFont(new Font("Tahoma", Font.BOLD, 20));
        InformationBox2.setBounds(42, 262, 50, 23);
        frame.getContentPane().add(InformationBox2);
    }
}