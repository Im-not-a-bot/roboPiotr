package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name = "Red Side Auto", group = "Autonomous")
public class RedTeamAuto extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    RobotHardware robot;

    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot = new RobotHardware(hardwareMap);

        waitForStart();
        runtime.reset();

        // autonomous start
        robot.move(40, 0, 0.1);
        robot.carousel.setPower(-1);
        robot.move(30, 0, 0.02);
        robot.carousel.setPower(0);
        robot.move(-5, 0, 0.03);

        robot.move(30, 30, 0.02);

        telemetry.addData("Finished", "RT: " + runtime.toString());
        telemetry.update();
    }

}