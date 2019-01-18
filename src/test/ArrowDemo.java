/* Erstellt am 02.10.2004 */
package test;
import java.awt.*; 
import java.awt.geom.*;  
import java.awt.event.*;  

public class ArrowDemo extends Component {  
  /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 7496472044172546974L;
GeneralPath arrow = new GeneralPath();  
  Stroke dashStroke =  
            new BasicStroke(1, BasicStroke.CAP_ROUND,  BasicStroke.JOIN_ROUND, 10f,  
       new float[]{4f, 4f}, 0f);  
  Paint gradientPaint =  
                   new GradientPaint(-20, 0, new Color(76, 181, 232),  
         20, 0, new Color(197, 0, 103));  
  int margin = 20; 
  Image texture;

  public void paint(Graphics _g){  
    // Casting to Graphics2D gives us access to the  
    // new 2D features.  
    Graphics2D g = (Graphics2D)_g;  
    
    // Control Rendering quality. Here, we set antialiasing  
    // on to get a smoother rendering.  
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  
          RenderingHints.VALUE_ANTIALIAS_ON);  

    // We will reuse the graphics’ default transform,
    // so we keep a reference to it.
    AffineTransform graphicsDefaultTransform = g.getTransform();

    // We use an affine transform to define how user  
    // coordinates should be transformed before rendering on  
    // the output device, i.e. how to transform into device  
    // coordinate. This allows us to reuse the same shape  
    // and draw it at different locations.  
    AffineTransform transform = new AffineTransform();  

    // The origin and arrow bounds are used to define the  
    // placement of each arrow. Starting at startPoint, arrows  
    // are drawn on a single row, with a margin.  
    Rectangle bounds = arrow.getBounds();  
    Point startPoint = new Point(20, 20);  

    // Draw a stroked arrow : use Graphics2D.draw  
    // First arrow is painted at the origin.  
    transform.translate(startPoint.x - bounds.x, startPoint.y - bounds.y);  
    g.transform(transform);  
    g.setStroke(dashStroke);  
    g.draw(arrow);  

    // Draw filled arrow : use Graphics2D.fill  
    // Second arrow is drawn to the right of the previous  
    // one, with a margin.  
    transform.translate(bounds.width+margin, 0);  
    g.setTransform(graphicsDefaultTransform);
    g.transform(transform);  
    g.setPaint(gradientPaint);  
    g.fill(arrow);  

    // Draw a rotated arrow by using rotation transform 
    transform.translate(bounds.width + margin, 0);  
    g.setTransform(graphicsDefaultTransform);
    g.transform(transform);  
    g.rotate(-Math.PI/4.);  
    g.fill(arrow);  

    // Draw a textured arrow by using the arrow shape
    // as a clipping area. Only do this if texture load
    // was successful.
    if(texture!=null){
      g.setTransform(graphicsDefaultTransform); // Revert to default
      transform.translate(bounds.width+margin, 0);  
      Shape newClip = transform.createTransformedShape(arrow);  
      g.clip(newClip);  
      g.drawImage(texture, newClip.getBounds().x, newClip.getBounds().y, this);
    }
  }  

  public ArrowDemo(){  
    arrow.moveTo(-20f,-10f);  
    arrow.lineTo(0f, -10f);  
    arrow.lineTo(0f, -20f);  
    arrow.lineTo(20f, 0f);  
    arrow.lineTo(0f, 20f);  
    arrow.lineTo(0f, 10f);  
    arrow.lineTo(-20f, 10f);  
    arrow.lineTo(-20f, -10f);  

    // Load texture image
    MediaTracker tracker = new MediaTracker(this);
    texture = Toolkit.getDefaultToolkit().getImage("Texture.jpg");
    tracker.addImage(texture, 0);
    try{
      tracker.waitForAll();
    }catch(InterruptedException e){
      texture=null;
    }

    // Add hit detector to check on rotated arrow hits 
    addMouseListener(new MouseAdapter(){  
      Rectangle hitRect = new Rectangle(0, 0, 5, 5);  
      public void mouseClicked(MouseEvent evt){  
        hitRect.x = evt.getX();  
        hitRect.y = evt.getY();  
        Graphics2D g = (Graphics2D)getGraphics();  

        AffineTransform transform = new AffineTransform();  
        Rectangle bounds = arrow.getBounds();  
        Point startPoint = new Point(20, 20);  
    
        transform.setToTranslation(startPoint.x - bounds.x + 2*bounds.width+2*margin, startPoint.y - bounds.y);  
        transform.rotate(-Math.PI/4.);  
        g.setTransform(transform);  
        if(g.hit(hitRect, arrow, false))  
   System.out.println("Rotated arrow was hit");  
      }  
    });  
  }  

  public static void main(String args[]){  
    Frame frame = new Frame("Shape in Graphics2D API");  
    frame.add(new ArrowDemo());  
    frame.setBackground(Color.white);
    frame.setSize(new Dimension(270, 110));  
    frame.setVisible(true);  
  }  
}
