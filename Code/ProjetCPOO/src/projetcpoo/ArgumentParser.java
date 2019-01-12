package projetcpoo;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Class pour parser les arguments de la ligne de commande
 */
public class ArgumentParser 
{
    public String i, prg, s, f, o;
    public double x, y, d, z;
    public int m, p, rx, ry, t;
	
    /**
     *
     * @param args : la liste des arguments à parser 
     */
    public ArgumentParser(String[] args) {
        Parse(args);
    }
	
    private void Parse(String[] args)
    {
	Options options = new Options();

        Option interactive = new Option("i", "interactive", true, "Set the interactive mode : On/Off.\nDefault value : On");
        interactive.setRequired(false);    
        options.addOption(interactive);
        
        Option progressive = new Option("prg", "progressive", true, "Set the progressive mode : On/Off.\nDefault value : Off");
        progressive.setRequired(false);    
        options.addOption(progressive);

        Option set = new Option("s", "set", true, "Set the fractal-set type : Julia/Mandelbrot.\nDefault value : Julia");
        set.setRequired(false);
        options.addOption(set);
        
        Option cx = new Option("cx", "x-constant", true, "Set the x value of the constant variable.\nDefault value : -0.8");
        cx.setRequired(false);
        options.addOption(cx);
        
        Option cy = new Option("cy", "y-constant", true, "Set the y value of the constant variable.\nDefault value : 0.156");
        cy.setRequired(false);
        options.addOption(cy);
              
        Option m_iter = new Option("m", "max-iter", true, "Set the number of max iterations.\nDefault value : 64");
        m_iter.setRequired(false);
        options.addOption(m_iter);
        
        Option function = new Option("f", "function", true, "Set the function type : Polynomial/Exponential.\nDefault value : Polynomial");
        function.setRequired(false);
        options.addOption(function);
        
        Option degree = new Option("d", "degree", true, "Set the function's degree : x >= 2.\nDefault value : 2");
        degree.setRequired(false);
        options.addOption(degree);
        
        Option palette = new Option("p", "palette", true, "Set the palette type : 1 or 2 or 3.\nDefault value : 1");
        palette.setRequired(false);
        options.addOption(palette);  
               
        Option zoom = new Option("z", "zoom", true, "Set the zoom value.\nDefault value : 1.0");
        zoom.setRequired(false);
        options.addOption(zoom);
                
        Option resolution = new Option("r", "resolution", true, "Set resolution size of the output picture.\nDefault value : 1000x1000");
        resolution.setRequired(false);
        options.addOption(resolution);
        
        Option out = new Option("o", "out", true, "Set the output file.\nDefault value : output.png");
        out.setRequired(false);
        options.addOption(out);
        
        Option thread = new Option("t", "thread", true, "Set the number of threads.\nDefault value : 1");
        thread.setRequired(false);
        options.addOption(thread);
        
        Option help = new Option("h", "help", false, "Print the help manual");
        help.setRequired(false);    
        options.addOption(help);
       
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try 
        {
            cmd = parser.parse(options, args);
       	} 
        catch (ParseException e) 
        {
            System.out.println(e.getMessage());
            formatter.printHelp("Optional arguments :", options);
            System.out.println("\n\nAuthors : Oussama Bouzaouit && Yanis Alim");
            System.exit(1);
        }
        
        if (cmd.hasOption("h"))
        {
            formatter.printHelp("Optional arguments :", options);
            System.out.println("Authors : Oussama Bouzaouit && Yanis Alim");
            System.exit(1);
        }
        
        String iValue = cmd.getOptionValue("interactive") != null ? cmd.getOptionValue("interactive").toLowerCase() : "on";
        String prgValue = cmd.getOptionValue("progressive") != null ? cmd.getOptionValue("progressive").toLowerCase() : "off";
        String sValue = cmd.getOptionValue("set") != null ? cmd.getOptionValue("set").toLowerCase() : "julia";
        String xValue = cmd.getOptionValue("cx") != null ? cmd.getOptionValue("cx") : "-0.8" ;
        String yValue = cmd.getOptionValue("cy") != null ? cmd.getOptionValue("cy") : "0.156" ;
        String fValue = cmd.getOptionValue("function") != null ? cmd.getOptionValue("function").toLowerCase() : "polynomial";
        String dValue = cmd.getOptionValue("degree") != null ? cmd.getOptionValue("degree") : "2" ;
        String mValue = cmd.getOptionValue("m_iter") != null ? cmd.getOptionValue("m_iter") : "64" ;
        String pValue = cmd.getOptionValue("palette") != null ? cmd.getOptionValue("palette") : "1" ;
        String zValue = cmd.getOptionValue("zoom") != null ? cmd.getOptionValue("zoom") : "1.0" ;
        String rValue = cmd.getOptionValue("resolution") != null ? cmd.getOptionValue("resolution") : "1000x1000" ;
        String tValue = cmd.getOptionValue("thread") != null ? cmd.getOptionValue("thread") : "1" ;
        String oValue = cmd.getOptionValue("output") != null ? cmd.getOptionValue("output") : "output" ;
        
        if ( ! iValue.equals("on") &&  ! iValue.equals("off") )
        {
            System.out.println("-i/--interactive should be : On/Off");
            System.exit(1);
        }
        i = iValue;
        
        if ( ! prgValue.equals("on") &&  ! prgValue.equals("off") )
        {
            System.out.println("-prg/--progressive should be : On/Off");
            System.exit(1);
        }
        prg = prgValue;
        
        if ( ! sValue.equals("julia") &&  ! sValue.equals("mandelbrot") )
        {
            System.out.println("-s/--set should be : Julia/Mandelbrot");
            System.exit(1);
        }
        s = sValue;
        
        try 
        {
            x = Double.parseDouble(xValue);
        } 
        catch (NumberFormatException e) 
        {
            System.out.println(e.getMessage());
            System.out.println("-x/-x-offeset should be a double value.");
            System.exit(1);        
        }
        
        try 
        {
            y = Double.parseDouble(yValue);
        } 
        catch (NumberFormatException e) 
        {
            System.out.println(e.getMessage());
            System.out.println("-y/-y-offeset should be a double value.");
            System.exit(1);        
        }
        
        try 
        {
            m = Integer.parseInt(mValue);
        } 
        catch (NumberFormatException e) 
        {
            System.out.println(e.getMessage());
            System.out.println("-m/--max-iter should be an integer value.");
            System.exit(1);        
        }
        
        if ( ! fValue.equals("polynomial") &&  ! fValue.equals("exponential") )
        {
            System.out.println("-f/--function should be : Polynomial/Exponential");
            System.exit(1);
        }
        f = fValue;
        
        try 
        {
            d = Integer.parseInt(dValue);
            if ( d < 2 )
            {
                System.out.println("-d/--degree should be higher or equal to 2.");
                System.exit(1);
            }   
        } 
        catch (NumberFormatException e) 
        {
            System.out.println(e.getMessage());
            System.out.println("-d/--degree should be an integer value.");
            System.exit(1);        
        }
        
        try 
        {
            p = Integer.parseInt(pValue);
            if ( p < 1 || p > 3 )
            {
                System.out.println("-p/--palette should be : 1 or 2 or 3.");
                System.exit(1);
            }   
        } 
        catch (NumberFormatException e) 
        {
            System.out.println(e.getMessage());
            System.out.println("-p/--palette should be an integer value.");
            System.exit(1);        
        }
       
        try 
        {
            z = Double.parseDouble(zValue);
        } 
        catch (NumberFormatException e) 
        {
            System.out.println(e.getMessage());
            System.out.println("-z/--zoom should be a double value.");
            System.exit(1);        
        }
        
        if ( ! rValue.contains("x") )
        {
            System.out.println("-r/--resolution should have this format : Width x Height (1000x1000).");
            System.exit(1); 
        }
        else
        {
            String[] res = rValue.split("x");
            try 
            {
                rx = Integer.parseInt(res[0]);
                if ( rx < 400)
                {
                    System.out.println("The width of the picture should be higher than 400.");
                    System.exit(1);
                }   
            } 
            catch (NumberFormatException e) 
            {
            	System.out.println(e.getMessage());
            	System.out.println("-r/--resolution should have this format : Width x Height (1000x1000).");
                System.exit(1);        
            }
        	
            try 
            {
                ry = Integer.parseInt(res[1]);
                if ( ry < 400)
                {
                     System.out.println("The height of the picture should be higher than 400.");
                     System.exit(1);
                }   
            } 
            catch (NumberFormatException e) 
            {
            	System.out.println(e.getMessage());
            	System.out.println("-r/--resolution should have this format : Width x Height (1000x1000).");
                System.exit(1);        
            }
        }
        
        try 
        {
            t = Integer.parseInt(tValue);
            if ( t < 1)
            {
                System.out.println("-t/--thread should be greater than 0.");
                System.exit(1);
            }   
        } 
        catch (NumberFormatException e) 
        {
            System.out.println(e.getMessage());
            System.out.println("-t/--thread should be an integer value.");
            System.exit(1);        
        }
        
        o = oValue + ".png";
    }
    
    
}

