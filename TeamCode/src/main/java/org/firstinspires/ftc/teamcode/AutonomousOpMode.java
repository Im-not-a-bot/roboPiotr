package org.firstinspires.ftc.teamcode;
/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import android.text.method.MovementMethod;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
//import com.qualcomm.robotcore.util.Range;

//import static com.qualcomm.robotcore.hardware.Servo.Direction.REVERSE;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 * <p>
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 * <p>
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name = "AutonomousOpMode", group = "Autonomous")
public class AutonomousOpMode extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    // Motor on the front left
    public DcMotor leftDrive1;
    // Motor on the front right
    public DcMotor rightDrive1;
    // Motor on the back left
    public DcMotor leftDrive2;
    // Motor on the back right
    public DcMotor rightDrive2;
    // Conveyor belt
    public DcMotor conveyorBelt;
    // Left shooter motor (ultraplanetary)
    public DcMotor leftShooter;
    // Right shooter motor (ultraplanetary)
    public DcMotor rightShooter;
    // Servo to turn the conveyor
    public Servo conveyorServo;
    //two color sensors
    RevColorSensorV3 colorsensor1;
    RevColorSensorV3 colorsensor2;

    int rings = 0;






    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // DAVID PETRE: Initialize hardware vars, see note below.
        // Be advised, until robot is properly configured, the devicenames are PLACEHOLDERS.
        // We need to replace them with what we eventually write down when we config

        leftDrive1 = hardwareMap.get(DcMotor.class, "left_front_drive");
        rightDrive1 = hardwareMap.get(DcMotor.class, "right_front_drive");
        leftDrive2 = hardwareMap.get(DcMotor.class, "left_back_drive");
        rightDrive2 = hardwareMap.get(DcMotor.class, "right_back_drive");
        conveyorBelt = hardwareMap.get(DcMotor.class, "conveyor");
        leftShooter = hardwareMap.get(DcMotor.class, "left_shooter");
        rightShooter = hardwareMap.get(DcMotor.class, "right_shooter");
        conveyorServo = hardwareMap.get(Servo.class, "floorAntiscuffer");
        leftDrive2.setDirection(DcMotorSimple.Direction.REVERSE);
        leftDrive1.setDirection(DcMotorSimple.Direction.REVERSE);
        colorsensor1 = (RevColorSensorV3) hardwareMap.get("sensor1");
        colorsensor2 = (RevColorSensorV3) hardwareMap.get("sensor2");


        // runs the moment robot is initialized
        waitForStart();
        runtime.reset();
        conveyorServo.setPosition(0);


        // runs after driver presses play
        while (opModeIsActive()) {
/*
            raiseConveyor();sleep(500);
            lateralDrive(15,-1);
            sleep(100);
            move(24,1);
*/
            raiseConveyor();
            sleep(1000);
            move(90,1);
            move(10,-1);

            /*
            if(colorsensor1.red()<250){telemetry.addData("rings:", 0);rings=0;}
            if(colorsensor1.red()>250&&colorsensor2.red()<250){telemetry.addData("rings",1);rings=1;};
            if(colorsensor1.red()>250&&colorsensor2.red()>250){telemetry.addData("rings:",4);rings=4;}
*/





/*
            lowerConveyor();
            startShooter();
            sleep(300);
            startConveyor();
            sleep(2500);
            stopConveyor();
            stopShooter();
            raiseConveyor();

            stop();

  */

            telemetry.addData("R1", colorsensor1.red());
            telemetry.addData("G1", colorsensor1.green());
            telemetry.addData("B1", colorsensor1.blue());
            telemetry.addData("A1", colorsensor1.alpha());
            telemetry.addData("R1", colorsensor2.red());
            telemetry.addData("G1", colorsensor2.green());
            telemetry.addData("B1", colorsensor2.blue());
            telemetry.addData("A1", colorsensor2.alpha());
            //telemetry.addData("likely color:", colorsensor1.getColor());
/*
            if(colorsensor1.red()<250){ZoneA();}
            if(colorsensor1.red()>250&&colorsensor2.red()<250){ZoneB();};
            if(colorsensor1.red()>250&&colorsensor2.red()>250){ZoneC();}
*/
            telemetry.update();
            //lowerConveyor();
            conveyorServo.setPosition(.5);

            sleep(1000);
            stop();

        }
    }

    public void ZoneA(){
        move(30,1);
        lateralDrive(13,1);
    }
    public void ZoneB(){
        move(58,1);
        lateralDrive(15,-1);
    }
    public void ZoneC(){
        move(72,1);
        lateralDrive(13,1);
    }

    public void move(int inches, int direction){
        if (direction == -1){
            leftDrive1.setPower(1);
            rightDrive1.setPower(.99);
            leftDrive2.setPower(1);
            rightDrive2.setPower(.99);
            sleep(inches * 40);
            leftDrive1.setPower(0);
            rightDrive1.setPower(0);
            leftDrive2.setPower(0);
            rightDrive2.setPower(0);
        }
        if (direction == 1){
            leftDrive1.setPower(-1);
            rightDrive1.setPower(-.99);
            leftDrive2.setPower(-1);
            rightDrive2.setPower(-.99);
            sleep(inches * 40);
            leftDrive1.setPower(0);
            rightDrive1.setPower(0);
            leftDrive2.setPower(0);
            rightDrive2.setPower(0);
        }
    }
    public void turn(int direction, int degrees){
        if (direction == 1){
            leftDrive1.setPower(1);
            leftDrive2.setPower(1);
            rightDrive1.setPower(-1);
            rightDrive2.setPower(-1);
        }
        if (direction == 1){
            leftDrive1.setPower(-1);
            leftDrive2.setPower(-1);
            rightDrive1.setPower(1);
            rightDrive2.setPower(1);
        }
        sleep(degrees/2);
        sleep(degrees*2);
    }
    public void jobsDone(){
        leftDrive1.setPower(0);
        leftDrive2.setPower(0);
        rightDrive1.setPower(0);
        rightDrive2.setPower(0);
        leftShooter.setPower(0);
        rightShooter.setPower(0);
        conveyorServo.setPosition(1);
        conveyorBelt.setPower(0);
    }

    public void lowerConveyor(){
        conveyorServo.setPosition(0);
    }
    public void raiseConveyor(){
        conveyorServo.setPosition(1);
    }
    public void startShooter(){
        leftShooter.setPower(-1);
        rightShooter.setPower(1);
    }
    public void stopShooter(){
        leftShooter.setPower(0);
        rightShooter.setPower(0);
    }
    public void startConveyor(){
        conveyorBelt.setPower(1);
    }
    public void stopConveyor(){
        conveyorBelt.setPower(0);
    }

    public void lateralDrive(int inches, int direction){
        if (direction == -1){
        leftDrive1.setPower(-1);
        leftDrive2.setPower(.98);
        rightDrive1.setPower(1);
        rightDrive2.setPower(-.98);
        }
        if (direction == 1) {
            leftDrive1.setPower(1);
            leftDrive2.setPower(-.98);
            rightDrive1.setPower(-1);
            rightDrive2.setPower(.98);
        }
        sleep(inches*60);
    }
}