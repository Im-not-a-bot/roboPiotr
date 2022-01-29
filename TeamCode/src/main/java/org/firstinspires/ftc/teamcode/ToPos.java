package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "broop", group = "TESTS")
public class ToPos extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    RobotHardware robot;

    double tpos=0;

    long now;
    long notNow;

    double cvel;
    double tvel;
    int cop;

    int cpos;
    int ppos;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot = new RobotHardware(hardwareMap);

        waitForStart();
        runtime.reset();

        while(opModeIsActive()) {

                notNow = now;
                ppos=cpos;


            now= System.nanoTime();

            cpos=robot.arm.getCurrentPosition();

            cvel=(cpos-ppos);

            tpos+=(gamepad1.right_stick_y/100);

            if(robot.arm.getCurrentPosition()<tpos){robot.arm.setDirection(DcMotorSimple.Direction.FORWARD);}
            if(robot.arm.getCurrentPosition()>tpos){robot.arm.setDirection(DcMotorSimple.Direction.FORWARD);}





            telemetry.addLine("automatically adjusting to position "+(int)tpos);
            telemetry.addLine("current position "+(int)cpos);
            telemetry.addLine("current velocity "+(int)cvel);
            telemetry.addLine("prev. position   "+(int)ppos);
            telemetry.addLine("update time (ms) "+((now-notNow)/1000000));

            telemetry.update();
        }
    }
}