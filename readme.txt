Basics:


Algorithm description:
URPaint is a simple Java program that allows users to draw and paint squares and circles of different colors on a black background. The program uses several classes and abstract classes to create shapes and panels, which are then combined in the main URPaint class to create the paint application.

The program starts by defining the ButtonEvent and ShapeType classes to represent the four buttons (square, circle, palette, and clear) and the two types of shapes (square and circle), respectively. The Shape abstract class is then defined to provide methods that are common to both the Square and Circle classes, which represent the two types of shapes that can be drawn in the application. The Square and Circle classes provide implementations of the methods defined in the Shape class and define the specifics of how squares and circles are drawn.

The program also defines several panels, including the SliderPanel, ColorPanel, and ButtonPanel classes, which are used to create the sliders, color picker, and buttons that are used to control the application. The ControlPanel class combines the ButtonPanel and ColorPanel classes, and the PaintPanel class is used to draw the shapes on the screen. The URPaint class brings all of these panels together to create the final paint application.

When the application is run, the ControlPanel and PaintPanel classes are created and added to the main URPaint frame. The ControlPanel provides the user with buttons and sliders to control the color and type of shape that is being drawn. The PaintPanel provides the canvas on which the shapes are drawn and defines the methods that allow the user to interact with the shapes, including clicking on them to activate and dragging them to move them.

Self-review:
Overall, the program appears to be functioning correctly and without any major bugs. However, there may be minor defects or issues that could be improved. For example, the program does not provide any error handling for invalid input or user actions that are not supported. Additionally, the program could be improved with additional features such as the ability to resize shapes or undo/redo actions.

Metadata:
The total time spent on this assignment is estimated to be 10 hours. There were no particularly tricky bugs encountered during the development of this program.


The Square and Circle button control the type of shape to add when mouse clicked.

When the program start, the default shape type is square.

When mouse clicked in the paint panel, a shape will be added according to the current shape type.
The new added shape is centered on click location.
The new added shape is automatically set as active shape.

If mouse click at the blank area in the paint panel, the active shape will be unselected.

If no active shape currently, a new shape will be added.

Squares and circles can be dragged smoothly.

RGB Sliders can change the color of active shape and paint panel background.
If it has an active shape currently, RGB Sliders will change the color of the active shape.
If no active shape currently, RGB Sliders will change the color of paint panel background.

The palette button can show a color chooser, the selected color will be used when create new shape.
