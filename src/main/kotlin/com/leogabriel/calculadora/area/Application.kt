import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import kotlin.math

@SpringBootApplication
class MyApplication

fun main(args: Array<String>) {
    runApplication<MyApplication>(*args)
}

@RestController
@GetMapping("/calculate")
class CalculatorController {

	data class CilinderResult(val perimetro: Double, val area: Double, val raio: Double, val diametro: Double, val volume: Double, val cumprimento: Double, val altura: Double)
    data class SphereResult(val perimetro: Double, val area: Double, val raio: Double, val diametro: Double, val volume: Double)
	data class CubeResult(val perimetro: Double, val area: Double, val volume: Double, val lado: Double)
	data class ConeResult(val perimetro: Double, val area: Double, val volume: Double, val lado: Double)

    @GetMapping("/cilinder/")
    fun calculateCilinder(@RequestParam perimetro: Double, @RequestParam area: Double, @RequestParam raio: Double, @RequestParam diametro: Double, @RequestParam volume: Double, @RequestParam cumprimento, @RequestParam altura: Double): CilinderResult {
		// diametro
		if (raio && diametro == null) {
			diametro = raio * 2
		}

		// raio
		if (diametro && raio == null) {
			raio = diametro / 2
		}

		// altura: volume / (π × raio²) 
		if (volume && raio && altura == null) {
			altura = volume / (math.PI * raio ** 2) 
		}

		// volume: π r² h
		if (raio && altura && volume == null) {
			volume = math.PI * raio ** 2 * altura
		}

		// area: 2π r h + 2π r².
		if (altura && raio && area == null) {
			area = 2 * math.PI * altura + 2 * math.PI * raio ** 2
		}

		// cumprimento: 2πr(r + h).
		if (altura && raio && cumprimento == null) {
			cumprimento = 2 * math.PI * raio * (math.PI + altura)
		}

		return CilinderResult(perimetro, area, raio, diametro, volume, cumprimento, altura);
	}

	@GetMapping("/cone/")
	fun calculateCone(@RequestParam area: Double, @RequestParam areaBase: Double, @RequestParam volume: Double,  @RequestParam altura: Double, @RequestParam raio: Double, @requestParam diametro: Double): ConeResult {
		
		// raio:  d/2
		if (diametro && raio == null) {
			raio = diametro / 2
		}

		// area da base: π·r²
		if (raio && areaBase == null) {
			areaBase = math.PI * raio ** 2
		}

		// volume: 1/3 * π * r² * h
		if (raio && altura && volume == null) {
			volume = 1/3 * math.PI * raio ** 2 * altura
		}

		// area: π * r * (r + g)
		if (raio && altura && area == null) {
			area = math.PI * raio * (raio + math.sqrt(raio ** 2 + altura ** 2))
		}

		return ConeResult(area, areaBase, volume, altura, raio, diametro);
	}

	@GetMapping("/sphere/")
	fun calculateSphere(@RequestParam perimetro: Double, @RequestParam area: Double, @RequestParam raio: Double, @RequestParam diametro: Double, @RequestParam volume: Double): SphereResult {

		if (raio && diametro == null) {
			diametro = raio*2
		}
		
		if (diametro && raio == null) {
			raio = diametro/2
		}

		if (raio && perimetro = null) {
			perimetro = 2 * math.PI * raio // Circunferencia
		}

		if (raio && area == null) {
			area = math.PI * raio**2
		}

		if (raio && volume == null) {
			volume = 4/3 * math.PI * raio**3
		}

		return SphereResult(perimetro, area, raio, diametro, volume)
	}

	@GetMapping("/cube/")
	fun calculateCube(@RequestParam perimetro: Double, @RequestParam area: Double, @RequestParam volume, @RequestParam lado): CubeResult {
		
		if (area && lado == null) {
			lado = math.sqrt(area / 6)
		}

		if (volume && lado == null) {
			lado = math.cbrt(volume)
		}

		if (lado && perimetro == null) {
			perimetro = lado * 12
		}

		if (lado && area == null) {
			area = lado ** 2 * 6
		}
		
		if (lado && volume == null) {
			volume = lado ** 3
		}

		if (area && lado == null) {
			lado = math.sqrt(area / 6)
		}

		return CubeResult(perimetro, area, volume, lado);
	
	}
}