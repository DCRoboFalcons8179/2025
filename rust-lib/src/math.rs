#[unsafe(no_mangle)]
pub extern "C" fn Java_frc_lib_RustMath_cutoffFilter(
    value: f64,
    max: f64,
    min: f64,
) -> f64 {
    if value > max {
        max
    } else if value < min {
        min
    } else {
        value
    }
}

#[unsafe(no_mangle)]
pub extern "C" fn Java_frc_lib_RustMath_powerCurce(
    value: f64,
    power: f64,
) -> f64 {
    // Preserve the sign
    let sign = if value < 0.0 { -1.0 } else { 1.0 };
    // Absolute value
    let abs_val = value.abs();

    // Computer abs_val * power
    let powered_value = abs_val.powf(power);

    if powered_value.is_infinite() {
        0.0
    } else {
        powered_value * sign
    }
}


#[unsafe(no_mangle)]
pub extern "C" fn Java_frc_lib_RustMath_getAverageSpeed(
    speeds: *const f64,
    angles: *const f64,
    len: usize,
) -> f64 {
    // Gets the speeds/angles as arrays
    let speeds = unsafe { std::slice::from_raw_parts(speeds, len) };
    let angles = unsafe { std::slice::from_raw_parts(angles, len) };

    // Initialize variables
    let mut vx = 0.0;
    let mut vy = 0.0;

    // For each index, add the speed * cos or sin of the angle
    for i in 0..len {
        vx += speeds[i] * angles[i].cos();
        vy += speeds[i] * angles[i].sin();
    }

    // Pathagrean-theorum devided by the amount of modules
    ((vx * vx + vy * vy).sqrt()) / len as f64
}
