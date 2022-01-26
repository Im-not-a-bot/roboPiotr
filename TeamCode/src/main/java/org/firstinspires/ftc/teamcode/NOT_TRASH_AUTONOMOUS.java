package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name = "NOT TRASH (Autonomous ver.)", group = "Autonomous")
public class NOT_TRASH_AUTONOMOUS extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    RobotHardware robot;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot = new RobotHardware(hardwareMap);

        waitForStart();
        runtime.reset();

        // this can stay, but has to be changed for mecanum (i.e. new move method uses deg and magnitude)
        // autonomous start
        //robot.move(70, 0, 0.1); // 1 meter
        //robot.turn(360, 0.05);
        //telemetry.addData("Traveled 1m forwar
        //telemetry.addData("Turned 360deg in place");


        //telemetry.addData("Finished");
        telemetry.update();
    }
}