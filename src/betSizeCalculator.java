
import java.awt.*;
import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.text.*;

// KELLY CRITERION---  k=(P*B -(1-P))/B
public class betSizeCalculator extends JPanel implements PropertyChangeListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2871117821937551194L;
	//value for fields
	private double probability=.5;
	private double accountSize=1000;
	private double equityPrice=10;
	private double target=15;
	private double stop=5;
	
	//labels to identify fields
	private JLabel probabilityLabel;
	private JLabel targetLabel;
	private JLabel stopLabel;
	private JLabel accountSizeLabel;
	private JLabel equityPriceLabel;
	private JLabel optimumBetLabel;
	
	//strings for the labels
	private static String probabilityString = "Probability: ";
    private static String targetString= "Target Price($): ";
    private static String stopString = "Stop Price($): ";
    private static String accountSizeString = "Account Size($): ";
    private static String equityPriceString = "Current Price($): ";
    private static String optimumBetString="Optimum Bet(Shares): ";
    
    //fileds for the data entry
   	JFormattedTextField accountSizeField;
    JFormattedTextField equityPriceField;
    JFormattedTextField probabilityField;
    JFormattedTextField targetField;
    JFormattedTextField stopField;
    JFormattedTextField optimumBetField;
    
    //formats to format and parse numbers
    private NumberFormat probabilityFormat;
    private NumberFormat targetFormat;
    private NumberFormat stopFormat;
    private NumberFormat accountSizeFormat;
    private NumberFormat equityPriceFormat;
    private NumberFormat optimumBetFormat;
    
    public betSizeCalculator() 
    {
        super(new BorderLayout());
        setUpFormats();
        int optimumBet = computeOptimumBet(probability,
                                        target,stop,accountSize,
                                        equityPrice);

        //Create the labels.
        probabilityLabel = new JLabel(probabilityString);
        targetLabel= new JLabel(targetString);
        stopLabel = new JLabel(stopString);
        accountSizeLabel = new JLabel(accountSizeString);
        equityPriceLabel = new JLabel(equityPriceString);
        optimumBetLabel= new JLabel(optimumBetString);

        //Create the text fields and set them up.
        accountSizeField= new JFormattedTextField(accountSizeFormat);
    	accountSizeField.setValue(new Double(accountSize));
    	accountSizeField.setColumns(20);
    	accountSizeField.addPropertyChangeListener("value",this);
    	
    	equityPriceField= new JFormattedTextField(equityPriceFormat);
    	equityPriceField.setValue(new Double(equityPrice));
    	equityPriceField.setColumns(20);
    	equityPriceField.addPropertyChangeListener("value",this);
    	
    	targetField= new JFormattedTextField(targetFormat);
    	targetField.setValue(new Double(target));
    	targetField.setColumns(20);
    	targetField.addPropertyChangeListener("value",this);
    	
    	stopField= new JFormattedTextField(stopFormat);
    	stopField.setValue(new Double(stop));
    	stopField.setColumns(20);
    	stopField.addPropertyChangeListener("value",this);
    	
    	probabilityField= new JFormattedTextField(probabilityFormat);
    	probabilityField.setValue(new Double(probability));
    	probabilityField.setColumns(20);
    	probabilityField.addPropertyChangeListener("value",this);
    	
    	optimumBetField= new JFormattedTextField(optimumBetFormat);
        optimumBetField.setValue(new Double(optimumBet));
        optimumBetField.setColumns(20);
        optimumBetField.setEditable(false);
        optimumBetField.setForeground(Color.red);

        //Tell accessibility tools about label/textfield pairs.
        probabilityLabel.setLabelFor(probabilityField);
        targetLabel.setLabelFor(targetField);
        stopLabel.setLabelFor(stopField);
        equityPriceLabel.setLabelFor(equityPriceField);
        accountSizeLabel.setLabelFor(accountSizeField);
        optimumBetLabel.setLabelFor(optimumBetField);

        //Lay out the labels in a panel.
        JPanel labelPane = new JPanel(new GridLayout(0,1));
        labelPane.add(accountSizeLabel);
        labelPane.add(equityPriceLabel);
        labelPane.add(targetLabel);
        labelPane.add(stopLabel);
        labelPane.add(probabilityLabel);
        labelPane.add(optimumBetLabel);

        //Layout the text fields in a panel.
        JPanel fieldPane = new JPanel(new GridLayout(0,1));
        fieldPane.add(accountSizeField);
        fieldPane.add(equityPriceField);
        fieldPane.add(targetField);
        fieldPane.add(stopField);
        fieldPane.add(probabilityField);
        fieldPane.add(optimumBetField);

        //Put the panels in this panel, labels on left,
        //text fields on right.
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(labelPane, BorderLayout.CENTER);
        add(fieldPane, BorderLayout.LINE_END);
    }
    
    /** Called when a field's "value" property changes. */
    public void propertyChange(PropertyChangeEvent e) {
        Object source = e.getSource();
        if (source == accountSizeField) {
            accountSize = ((Number)accountSizeField.getValue()).doubleValue();
        } else if (source == equityPriceField) {
            equityPrice = ((Number)equityPriceField.getValue()).doubleValue();
        } else if (source == targetField) {
        	target = ((Number)targetField.getValue()).doubleValue();
        } else if(source == stopField) {
        	stop = ((Number)stopField.getValue()).doubleValue();
        } else if(source == probabilityField) {
        	probability = ((Number)probabilityField.getValue()).doubleValue();
        }	
        double optimumBet = computeOptimumBet(probability,target,stop,accountSize,equityPrice);
       	optimumBetField.setValue(new Double(optimumBet));
    }
	
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Bet Size Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add contents to the window.
        frame.add(new betSizeCalculator());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
	
	public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
	        UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
	}
        

	//Create and set up number formats. These objects also
    //parse numbers input by user.
    private void setUpFormats()
    {
        probabilityFormat = NumberFormat.getNumberInstance();
        probabilityFormat.setMinimumFractionDigits(2);

        targetFormat = NumberFormat.getNumberInstance();
        targetFormat.setMinimumFractionDigits(2);
        
        stopFormat = NumberFormat.getNumberInstance();
        stopFormat.setMinimumFractionDigits(2);
     
        equityPriceFormat = NumberFormat.getNumberInstance();
        equityPriceFormat.setMinimumFractionDigits(2);
        
        accountSizeFormat= NumberFormat.getNumberInstance();
        
        optimumBetFormat = NumberFormat.getNumberInstance();
    }

	private int computeOptimumBet(double prob,double target, double stop,  double acct, double price)
	{
		if((target>price && stop>price) || (target<price && stop<price) || (price==stop) || (target == price) || (price==0)){
			return 0;
		}
		
		double payout = (target-price)/(price-stop);
		double percentBet= (((prob*payout)-(1-prob))/payout);

		if(target>stop){
			return (int)Math.round((percentBet*acct)/(price-stop));
		}else{
			return -(int)Math.round((percentBet*acct)/(stop-price));

		}
		
		
	}
	

	

}
