package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

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


    public DcMotor FR;
    public DcMotor FL;
    public DcMotor BR;
    public DcMotor BL;

    public DcMotor arm;
    public DcMotor sweeper;
    public DcMotor carousel;
    public Servo armEnd;

    public RobotHardware(HardwareMap hardwareMap) {
        FR  = hardwareMap.get(DcMotor.class, "FR");
        FL = hardwareMap.get(DcMotor.class, "FL");
        BR = hardwareMap.get(DcMotor.class, "BR");
        BL = hardwareMap.get(DcMotor.class, "BL");
        arm = hardwareMap.get(DcMotor.class, "arm");
        sweeper = hardwareMap.get(DcMotor.class,"sweeper");
        armEnd = hardwareMap.get(Servo.class,"servo");
        carousel = hardwareMap.get(DcMotor.class,"carousel");

        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sweeper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        carousel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FR.setZeroPowerBehavior(BRAKE);
        FL.setZeroPowerBehavior(BRAKE);
        BR.setZeroPowerBehavior(BRAKE);
        BL.setZeroPowerBehavior(BRAKE);
        arm.setZeroPowerBehavior(BRAKE);
        sweeper.setZeroPowerBehavior(BRAKE);
        carousel.setZeroPowerBehavior(BRAKE);

        arm.setTargetPosition(0);

        //makes accuracy remotely possible
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        sweeper.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        carousel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        FR.setDirection(DcMotorSimple.Direction.FORWARD);
        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        arm.setDirection(DcMotorSimple.Direction.FORWARD);
        sweeper.setDirection(DcMotorSimple.Direction.FORWARD);
        carousel.setDirection(DcMotorSimple.Direction.FORWARD);


    }

    /*
    void move(double distance, double turn, double power) {

    }

    void turn(double deg, double power) {
        move(0, deg, power);
    }

     */
}