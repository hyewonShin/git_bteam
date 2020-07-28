$(document).ready(function() {
	var refreshInterval = null;
	var timer = null;
	var imgCnt = $('.slider_panel').children().length;
	var imgIdx = 1;
	
	function moveSlider(index) {
		var willMoveLeft = -(1920 * index);
			
			$('.slider_panel').animate({
				left: willMoveLeft
			}, 'slow');
			
			$('.slider_text[data-index = ' + index + ']').show().animate({
				left: 0
			}, 'slow');
			
			$('.slider_text[data-index != ' + index + ']').hide('slow', function() {
				$(this).css('left', 800);
			});
				
			$('.control_button[data-index =' + index +']').addClass('active');
			$('.control_button[data-index !=' + index +']').removeClass('active');
	}//moveSlider()
	
	var track = 0;
	timer = function() {
		moveSlider(imgIdx);	
		
		if (imgIdx >= 4) {
			track = -1;
		}
		
		if (imgIdx <= 0){
			track = 0;
		}
		
		if (track == 0) {
			imgIdx++;
		} 
		
		if (track == -1){
			imgIdx--;
		}
		
	}
	$('.control_button').each(function(index) {
		$(this).attr('data-index', index).click(function() {
			var index = $(this).attr('data-index');
			imgIdx = index;
			moveSlider(index);
		});
	});
	
	$('.slider_text').css('left', 800).each(function(index) {
		$(this).attr('data-index', index);
	});
	
	$('.slider_text[data-index =' + 0 +']').show().animate({
		left: 0
	}, 'slow');
	
	$('.control_button[data-index =' + 0 +']').addClass('active');
		
	refreshInterval = setInterval(timer, 3000);
	
	$('.animation_canvas').on({
		mouseenter: function() {
			clearInterval(refreshInterval);
		},
		mouseleave: function() {
			refreshInterval = setInterval(timer, 3000);
		}
	});
});