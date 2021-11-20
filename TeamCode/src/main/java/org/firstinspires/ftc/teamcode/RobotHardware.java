package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;

public class RobotHardware {
    // todo: fix these shitty constants. Will this still be a problem during mecanum wheels?

    // pie in the sky: ML with camera
    // coding for mecanum and linear extruder / claw
    // consistent abstractions (turning, moving)
    // anything else?
    final public static double ONE_METER = 400; // guess and check lmao
	final public static double ONE_CENTIMETER =  ONE_METER / 100;
	final public static double FULL_ROBOTATION = 240; // found by deriving the inverse sin of LMAO I'm just kidding guess and check
	final public static double ONE_DEGREE = FULL_ROBOTATION / 360;


    public DcMotor left;
    public DcMotor right;
    public DcMotor rightTray;
    public DcMotor leftTray;
    public CRServo carousel;

    public RobotHardware(HardwareMap hardwareMap) {
        left = hardwareMap.get(DcMotor.class, "left");
        right = hardwareMap.get(DcMotor.class, "right");
        rightTray = hardwareMap.get(DcMotor.class, "rightTray");
        leftTray = hardwareMap.get(DcMotor.class, "leftTray");
        carousel = hardwareMap.get(CRServo.class, "carousel");

        left.setDirection(DcMotor.Direction.FORWARD);
        right.setDirection(DcMotor.Direction.REVERSE);
        // peat didn't set these
        rightTray.setDirection(DcMotor.Direction.FORWARD);
        leftTray.setDirection(DcMotor.Direction.FORWARD);

        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightTray.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftTray.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    // all degrees are 'shitty unit circle': 0 degrees is front facing, then go clockwise
    // I'm going to worry about doing turns later... what do I look like, a math kid? trans. DON'T DO TURNS
    // power + turn, power - turn
    void move(double distance, double turn, double power) {
        int leftPosition = left.getCurrentPosition() + (int)(distance * ONE_CENTIMETER - turn * ONE_DEGREE);
        int rightPosition = right.getCurrentPosition() + (int)(distance * ONE_CENTIMETER + turn * ONE_DEGREE);


        left.setTargetPosition(leftPosition);
        right.setTargetPosition(rightPosition);

        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        left.setPower(power);
        right.setPower(power);

        while(left.isBusy() && right.isBusy()); // I think this is right? curse code duplication!

        left.setPower(0);
        right.setPower(0);

        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    void turn(double deg, double power) {
        move(0, deg, power);
    }
}