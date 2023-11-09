import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;

/**
 * Button event type of the Button Panel
 */

class ButtonEvent {
    public static int square = 1;
    public static int circle = 2;
    public static int palette = 3;
    public static int clear = 4;
}
//enum ButtonEvent {
//    square,
//    circle,
//    palette,
//    clear
//}


class ShapeType {
    public static int square = 1;
    public static int circle = 2;
}

/**
 * The type of shape
 */
//enum ShapeType {
//    square,
//    circle
//}

/**
 * The base class of shapes
 */
abstract class Shape {
    private Color color;

    public Shape(Color color) {
        this.color = color;
    }

    public abstract void paintComponent(Graphics g, boolean isActive);

    public abstract void move(int offsetX, int offsetY);

    public abstract void moveTo(Point point);

    public abstract boolean contain(Point point);

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}

/**
 * The square shape
 */
class Square extends Shape {

    private Point leftBottom;
    private int width;

    public Square(Color color, Point leftBottom, int width) {
        super(color);
        this.leftBottom = leftBottom;
        this.width = width;
    }


    public void paintComponent(Graphics g, boolean isActive) {
        g.setColor(getColor());
        g.fillRect(leftBottom.x, leftBottom.y, width, width);
        if (isActive) {
            Graphics2D g2d = (Graphics2D)g;
            g2d.setStroke(new BasicStroke(1));
            g.setColor(Color.CYAN);
            g.drawRect(leftBottom.x, leftBottom.y, width, width);
        }
    }


    public void move(int offsetX, int offsetY) {
        leftBottom.x += offsetX;
        leftBottom.y += offsetY;
    }


    public void moveTo(Point point) {
        leftBottom.x = point.x - width / 2;
        leftBottom.y = point.y - width / 2;
    }


    public boolean contain(Point point) {
        boolean inX = (point.x >= leftBottom.x) && (point.x <= leftBottom.x + width);
        boolean inY = (point.y >= leftBottom.y) && (point.y <= leftBottom.y + width);
        return inX && inY;
    }
}

/**
 * The circle shape
 */
class Circle extends Shape {

    private Point center;
    private int radius;

    public Circle(Color color, Point center, int radius) {
        super(color);
        this.center = center;
        this.radius = radius;
    }


    public void paintComponent(Graphics g, boolean isActive) {
        Point leftBottom = new Point(center.x - radius,center.y - radius);
        int side = 2 * radius;
        g.setColor(getColor());
        g.fillOval(leftBottom.x, leftBottom.y, side, side);
        if (isActive) {
            Graphics2D g2d = (Graphics2D)g;
            g2d.setStroke(new BasicStroke(1));
            g.setColor(Color.CYAN);
            g.drawOval(leftBottom.x, leftBottom.y, side, side);
        }
    }


    public void move(int offsetX, int offsetY) {
        center.x += offsetX;
        center.y += offsetY;
    }


    public void moveTo(Point point) {
        center.x = point.x;
        center.y = point.y;
    }


    public boolean contain(Point point) {
        return Math.sqrt(Math.pow(point.x - center.x, 2) + Math.pow(point.y - center.y, 2)) < radius;
    }
}

/**
 * The Slider panel which contains the slider and title label
 */
class SliderPanel extends JPanel {

    private JSlider slider;

    public SliderPanel(String title, int maxVal) {
        slider = new JSlider(0, maxVal);
        slider.setPaintLabels(true);
        Hashtable<Integer, JComponent> valueLabels = slider.createStandardLabels(maxVal);
        slider.setLabelTable(valueLabels);
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        add(new JLabel(title));
        add(slider);
    }

    public void setChangeListener(ChangeListener listener) {
        slider.addChangeListener(listener);
    }

    public JSlider getSlider() {
        return slider;
    }
}

/**
 * The color panel which contains three slider panels for rea, green and blue
 */
class ColorPanel extends JPanel {
    private SliderPanel rPanel;
    private SliderPanel gPanel;
    private SliderPanel bPanel;

    private URPaint.ControlPanel parentPanel;

    public ColorPanel(URPaint.ControlPanel parentPanel) {
        this.parentPanel = parentPanel;
        this.rPanel = new SliderPanel("R", 255);
        this.gPanel = new SliderPanel("G", 255);
        this.bPanel = new SliderPanel("B", 255);
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        add(rPanel);
        add(gPanel);
        add(bPanel);
        initChangeListener();
    }

    private void initChangeListener() {
        ChangeListener listener = new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                parentPanel.colorChangedHandler(getColor());
            }
        };
        rPanel.setChangeListener(listener);
        gPanel.setChangeListener(listener);
        bPanel.setChangeListener(listener);
    }

    public Color getColor() {
        int red = rPanel.getSlider().getValue();
        int green = gPanel.getSlider().getValue();
        int blue = bPanel.getSlider().getValue();
        return new Color(red, green, blue);
    }
}

/**
 * The button panel which contains four button: Square, Circle, Pallet and Clear
 */
class ButtonPanel extends JPanel {
    private JButton squareBtn;
    private JButton circleBtn;
    private JButton paletteBtn;
    private JButton clearBtn;

    private URPaint.ControlPanel parentPanel;

    public ButtonPanel(URPaint.ControlPanel parentPanel) {
        this.parentPanel = parentPanel;
        this.squareBtn = new JButton("Square");
        this.circleBtn = new JButton("Circle");
        this.paletteBtn = new JButton("Palette");
        this.clearBtn = new JButton("Clear");
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        add(squareBtn);
        add(circleBtn);
        add(paletteBtn);
        add(clearBtn);
        initActionListener();
    }

    private void initActionListener() {
        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JButton btn = (JButton) e.getSource();
                if (btn == squareBtn) {
                    parentPanel.buttonClickHandler(ButtonEvent.square);
                }else if (btn == circleBtn) {
                    parentPanel.buttonClickHandler(ButtonEvent.circle);
                }else if (btn == paletteBtn) {
                    parentPanel.buttonClickHandler(ButtonEvent.palette);
                }else if (btn == clearBtn) {
                    parentPanel.buttonClickHandler(ButtonEvent.clear);
                }
            }
        };
        squareBtn.addActionListener(listener);
        circleBtn.addActionListener(listener);
        paletteBtn.addActionListener(listener);
        clearBtn.addActionListener(listener);
    }
}

/**
 * The URPaint class
 */
public class URPaint extends JFrame {

    /**
     * The control panel which contains the button panel and color panel
     */
    class ControlPanel extends JPanel {
        private ButtonPanel buttonPanel;
        private ColorPanel colorPanel;

        private PaintPanel paintPanel;

        private int curType;

        private Color shapeColor;

        public ControlPanel() {
            this.buttonPanel = new ButtonPanel(this);
            this.colorPanel = new ColorPanel(this);
            this.curType = ShapeType.square;
            this.shapeColor = Color.green;
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            add(buttonPanel);
            add(colorPanel);
        }

        public void setPaintPanel(PaintPanel paintPanel) {
            this.paintPanel = paintPanel;
        }

        public void colorChangedHandler(Color newColor) {
            paintPanel.updateColor(newColor);
        }

        public void buttonClickHandler(int event) {
            if (event == ButtonEvent.square) {
                curType = ShapeType.square;
            }else if (event == ButtonEvent.circle) {
                curType = ShapeType.circle;
            }else if (event == ButtonEvent.palette) {
                shapeColor = JColorChooser.showDialog(this,"Pick Color",shapeColor);
            }else if (event == ButtonEvent.clear) {
                paintPanel.clearShapes();
            }
        }

        public Color getSliderColor() {
            return colorPanel.getColor();
        }

        public Color getShapeColor() {
            return shapeColor;
        }

        public int getCurType() {
            return curType;
        }
    }

    /**
     * The paint panel used to paint square and circle
     */
    class PaintPanel extends JPanel {
        private Shape[] shapes;
        private int amount;
        private int activeIndex;

        private ControlPanel controlPanel;

        private int pressedShapeIndex;

        public PaintPanel() {
            this.shapes = new Shape[1000];
            this.amount = 0;
            this.activeIndex = -1;
            initMouseAdapter();
            setBackground(Color.BLACK);
        }

        public void setControlPanel(ControlPanel controlPanel) {
            this.controlPanel = controlPanel;
        }


        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int i = 0; i < amount; i++) {
                shapes[i].paintComponent(g, (i == activeIndex));
            }
        }

        public void clearShapes() {
            amount = 0;
            activeIndex = -1;
            repaint();
        }

        public void updateColor(Color newColor) {
            if (activeIndex != -1) {
                shapes[activeIndex].setColor(newColor);
                repaint();
            }else {
                setBackground(newColor);
            }
        }

        private void initMouseAdapter() {
            MouseInputListener listener = new MouseInputListener() {

                public void mouseClicked(MouseEvent e) {
                   // System.out.println("1");
                    if (pressedShapeIndex != -1) {
                       // System.out.println("2");
                        activeIndex = pressedShapeIndex;
                        repaint();
                    }else {
                       // System.out.println("3");
                        if (activeIndex != -1) {
                            System.out.println("4");
                            activeIndex = -1;
                            repaint();
                        } else {
                           // System.out.println("5");
                            createShape(e.getPoint());
                        }
                    }
                }


                public void mousePressed(MouseEvent e) {
                    pressedShapeIndex = getPressedShapeIndex(e.getPoint());
                }


                public void mouseDragged(MouseEvent e) {
                    activeIndex = pressedShapeIndex;
                    if (activeIndex != -1) {
                        shapes[activeIndex].moveTo(e.getPoint());
                        repaint();
                    }
                }


                public void mouseReleased(MouseEvent e) {}
                public void mouseEntered(MouseEvent e) {}
                public void mouseExited(MouseEvent e) {}
                public void mouseMoved(MouseEvent e) {}
            };
            addMouseListener(listener);
            addMouseMotionListener(listener);
        }

        private int getPressedShapeIndex(Point point) {
            for (int i = 0; i < amount; i++) {
                if (shapes[i].contain(point)) {
                    return i;
                }
            }
            return -1;
        }

        private void createShape(Point center) {
            Color color = controlPanel.getShapeColor();
            int type = controlPanel.getCurType();
            Shape res = null;
            if (type == ShapeType.square) {
                res = new Square(color, new Point(center.x - 15, center.y - 15), 30);
            }else if (type == ShapeType.circle) {
                res = new Circle(color, center, 15);
            }
            if (res != null) {
                shapes[amount] = res;
                activeIndex = amount;
                amount++;
                repaint();
            }
        }
    }

    private ControlPanel controlPanel;
    private PaintPanel paintPanel;

    public URPaint() {
        this.controlPanel = new ControlPanel();
        this.paintPanel = new PaintPanel();
        this.controlPanel.setPaintPanel(this.paintPanel);
        this.paintPanel.setControlPanel(this.controlPanel);
        getContentPane().add(this.controlPanel, BorderLayout.PAGE_START);
        getContentPane().add(this.paintPanel, BorderLayout.CENTER);
        setTitle("PaintHW");
        setSize(500, 500);
        setVisible(true);
    }

    public static void main(String[] args) {
        new URPaint();
    }
}
