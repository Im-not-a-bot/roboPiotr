package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@Autonomous(name = "autonomous", group = "Autonomous")
public class autotest extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    RobotHardware r;
    @Override
    public void runOpMode() {
        waitForStart();

        r=new RobotHardware(hardwareMap);
        r.encoderDrive(0,10,0,1,r.CM);

    }
}