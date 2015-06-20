import org.w3c.dom.css.RGBColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by goatie on 19/06/15.
 */
public class Face extends JFrame {

    JTextField noOfVariables,noOfIterations;
    JLabel noOfVariablesLabel, noOfIterationsLabel;
    JButton generateForm;
    JPanel bottomPanel, topPanel, mainPanel;
    static ArrayList<Component> topComponents= new ArrayList();
    static ArrayList<Component> bottomComponents = new ArrayList();
    static String[] variable_names =    {"a","b","c","d","e","f","g","h","i","j","k", "l","m",
            "n","o","p","q","r","s","t","u","v","w","x","y","z"};

    Face(){
        super("Simplex Algorithm");
        setLayout(new FlowLayout());

        noOfVariablesLabel = new JLabel("No of Variables");
        noOfVariables = new JTextField(5);

        noOfIterationsLabel = new JLabel("No of Iterations");
        noOfIterations = new JTextField(5);

        generateForm = new JButton("Generate Form");

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        topPanel = new JPanel(new FlowLayout());
        topPanel.setBackground(Color.LIGHT_GRAY);

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));


        topComponents.add(noOfVariablesLabel);
        topComponents.add(noOfVariables);

        topComponents.add(noOfIterationsLabel);
        topComponents.add(noOfIterations);

        topComponents.add(generateForm);

        add(mainPanel);
        mainPanel.add(topPanel); mainPanel.add(bottomPanel);

        addItems(topPanel,topComponents);
        appointListeners();
    }

    public void createBottomComponents(){
        bottomPanel.removeAll();
        bottomComponents.clear();

        int number_of_variables  = Integer.parseInt(noOfVariables.getText());

        int i = 0;
        while(i<number_of_variables+1) {
            JPanel jPanel = new JPanel(new FlowLayout());
            jPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            if((i%2)==1){
                jPanel.setBackground(Color.GRAY);
            }
            else{
                jPanel.setBackground(Color.WHITE);
            }

            if (i == 0) {
                jPanel.add(new JLabel("Objective Function "),BorderLayout.NORTH);
            } else {
                jPanel.add(new JLabel(i+". Subject Function: "),BorderLayout.NORTH);
            }

            int j = 0;
            while (j <= number_of_variables) {//.*2 for the slack variables
                if (j < number_of_variables) {//if it is coeffiecent of x and y or z
                    jPanel.add(new JTextField(3),BorderLayout.SOUTH);
                    if(j<number_of_variables-1)//so the + wont appear after the last variable
                        jPanel.add(new JLabel(variable_names[j]+" + "),BorderLayout.SOUTH);
                    else
                        jPanel.add(new JLabel(variable_names[j]),BorderLayout.SOUTH);

                }
                else {//the value on the other side of the equal sign
                    if(i!=0) {//if its not the objective function
                        jPanel.add(new JLabel(" ="),BorderLayout.SOUTH);
                        jPanel.add(new JTextField(3),BorderLayout.SOUTH);
                    }
                }
                j++;
            }
            bottomComponents.add(jPanel);
            i++;
        }
        bottomComponents.add(new JButton("Iterate"));

        addItems(bottomPanel, bottomComponents);
    }

    public void appointListeners(){
        TheHandler theHandler = new TheHandler();
        generateForm.addActionListener(theHandler);
    }

    private class TheHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String string = "";
            if(actionEvent.getSource() == generateForm) {
                createBottomComponents();
                //string = String.format("Field 1 : %s", actionEvent.getActionCommand());
            }
            else{
                createBottomComponents();
            }
            //JOptionPane.showMessageDialog(null,string);
        }
    }

    public void addItems(JPanel jPanel, ArrayList<Component> component){
        for(Component c : component){
            jPanel.add(c,BorderLayout.WEST);
        }
        this.setVisible(true);
    }


}
