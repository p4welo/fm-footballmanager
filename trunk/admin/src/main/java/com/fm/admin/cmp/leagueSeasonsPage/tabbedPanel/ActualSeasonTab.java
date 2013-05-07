package com.fm.admin.cmp.leagueSeasonsPage.tabbedPanel;

import com.fm.admin.cmp.leagueSeasonsPage.chart.ChartPanel;
import com.fm.core.cmp.notify.Notification;
import com.fm.domain.League;
import com.fm.domain.Season;
import com.fm.domain.Team;
import com.fm.domain.TeamRecord;
import com.fm.service.ISeasonService;
import com.fm.service.ITeamRecordService;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * User: pawel.radomski
 * Date: 19.04.13
 * Time: 16:47
 */
public class ActualSeasonTab extends Panel
{
   @SpringBean
   private ISeasonService seasonService;

   @SpringBean
   private ITeamRecordService teamRecordService;

   private League league;

   private Season season;

   private List<TeamRecord> teamRecords;

   private ChartPanel chart;

   private Team selected;

   public ActualSeasonTab(String id, IModel<League> model)
   {
      super(id, model);
      setOutputMarkupId(true);
      league = model.getObject();
      season = seasonService.getActiveSeason(league);
      teamRecords = teamRecordService.findTeamRecordsBySeason(season, true);

      initView();
   }

   private void initView()
   {
      final ListView<TeamRecord> teamListView = new ListView<TeamRecord>("teams",
              new PropertyModel<List<? extends TeamRecord>>(this, "teamRecords"))
      {

         @Override
         protected void onBeforeRender()
         {
            teamRecords = teamRecordService.findTeamRecordsBySeason(season, true);
            super.onBeforeRender();
         }

         @Override
         protected void populateItem(final ListItem<TeamRecord> item)
         {
            final TeamRecord teamRecord = item.getModelObject();
            item.add(new Label("place", new PropertyModel<String>(teamRecord, TeamRecord.FIELD_PLACE)));
            item.add(new Label("name", new PropertyModel<String>(teamRecord, TeamRecord.FIELD_TEAM_NAME)));
            item.add(new Label("round", new PropertyModel<String>(teamRecord, TeamRecord.FIELD_ROUND_NUMBER)));
            item.add(new Label("points", new PropertyModel<String>(teamRecord, TeamRecord.FIELD_POINTS_COUNT)));
            item.add(new Label("goalsScored", new PropertyModel<String>(teamRecord, TeamRecord.FIELD_GOALS_SCORED)));
            item.add(new Label("goalsAllowed", new PropertyModel<String>(teamRecord, TeamRecord.FIELD_GOALS_ALLOWED)));
            item.add(new Label("goalsDifference",
                    new PropertyModel<String>(teamRecord, TeamRecord.FIELD_GOALS_DIFFERENCE)));
            item.add(new Label("wins", new PropertyModel<String>(teamRecord, TeamRecord.FIELD_WINS_COUNT)));
            item.add(new Label("draws", new PropertyModel<String>(teamRecord, TeamRecord.FIELD_DRAWS_COUNT)));
            item.add(new Label("loses", new PropertyModel<String>(teamRecord, TeamRecord.FIELD_LOSES_COUNT)));
            item.add(new AjaxEventBehavior("onclick")
            {
               @Override
               protected void onEvent(AjaxRequestTarget target)
               {
                  executeOnClick(target, item.getModel());
               }
            });
            if (teamRecord.getTeam().equals(selected))
            {
               item.add(AttributeModifier.append("class", "selectedElement"));
            }
         }
      };
      add(teamListView);

      add(new AjaxLink<Void>("newSeason")
      {
         @Override
         public void onClick(AjaxRequestTarget target)
         {
            season = seasonService.nextSeason(league);
            Notification.success(getString("next.season.successfully.generated"));
            target.add(ActualSeasonTab.this);
         }
      });

      add(new AjaxLink<Void>("nextRound")
      {
         @Override
         public void onClick(AjaxRequestTarget target)
         {
            teamRecords = teamRecordService.simulateNextRound(season);
            target.add(ActualSeasonTab.this);
         }

         @Override
         protected void onConfigure()
         {
            setVisible(season != null);
         }
      });
      chart = new ChartPanel("chartPanel", new PropertyModel<Team>(this, "selected"), season);
      chart.setOutputMarkupId(true);
      add(chart);
   }

   private void executeOnClick(AjaxRequestTarget target, IModel<TeamRecord> model)
   {
      selected = model.getObject().getTeam();
      target.add(ActualSeasonTab.this);
   }
}